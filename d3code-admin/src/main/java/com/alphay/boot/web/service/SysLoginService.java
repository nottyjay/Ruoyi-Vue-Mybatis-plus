package com.alphay.boot.web.service;

import javax.annotation.Resource;

import com.alphay.boot.common.constant.CacheConstants;
import com.alphay.boot.common.constant.Constants;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.core.domain.model.LoginUser;
import com.alphay.boot.common.core.redis.RedisCache;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.exception.user.CaptchaException;
import com.alphay.boot.common.exception.user.CaptchaExpireException;
import com.alphay.boot.common.exception.user.UserPasswordNotMatchException;
import com.alphay.boot.common.utils.DateUtils;
import com.alphay.boot.common.utils.MessageUtils;
import com.alphay.boot.common.utils.ServletUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.common.utils.ip.IpUtils;
import com.alphay.boot.security.service.TokenService;
import com.alphay.boot.system.service.ISysConfigService;
import com.alphay.boot.system.service.ISysUserService;
import com.alphay.boot.web.manager.factory.AsyncFactory;
import com.alphay.boot.web.security.context.AuthenticationContextHolder;
import com.alphay.boot.web.manager.AsyncManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author d3code
 */
@Component
public class SysLoginService {
  @Autowired private TokenService tokenService;

  @Resource private AuthenticationManager authenticationManager;

  @Autowired private RedisCache redisCache;

  @Autowired private ISysUserService userService;

  @Autowired private ISysConfigService configService;

  /**
   * 登录验证
   *
   * @param username 用户名
   * @param password 密码
   * @param code 验证码
   * @param uuid 唯一标识
   * @return 结果
   */
  public String login(String username, String password, String code, String uuid) {
    boolean captchaEnabled = configService.selectCaptchaEnabled();
    // 验证码开关
    if (captchaEnabled) {
      validateCaptcha(username, code, uuid);
    }
    // 用户验证
    Authentication authentication = null;
    try {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(username, password);
      AuthenticationContextHolder.setContext(authenticationToken);
      // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
      authentication = authenticationManager.authenticate(authenticationToken);
    } catch (Exception e) {
      if (e instanceof BadCredentialsException) {
        AsyncManager.me()
            .execute(
                AsyncFactory.recordLogininfor(
                    username,
                    Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.not.match")));
        throw new UserPasswordNotMatchException();
      } else {
        AsyncManager.me()
            .execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
        throw new ServiceException(e.getMessage());
      }
    } finally {
      AuthenticationContextHolder.clearContext();
    }
    AsyncManager.me()
        .execute(
            AsyncFactory.recordLogininfor(
                username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    recordLoginInfo(loginUser.getUserId());
    // 生成token
    return tokenService.createToken(loginUser);
  }

  /**
   * 校验验证码
   *
   * @param username 用户名
   * @param code 验证码
   * @param uuid 唯一标识
   * @return 结果
   */
  public void validateCaptcha(String username, String code, String uuid) {
    String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
    String captcha = redisCache.getCacheObject(verifyKey);
    redisCache.deleteObject(verifyKey);
    if (captcha == null) {
      AsyncManager.me()
          .execute(
              AsyncFactory.recordLogininfor(
                  username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
      throw new CaptchaExpireException();
    }
    if (!code.equalsIgnoreCase(captcha)) {
      AsyncManager.me()
          .execute(
              AsyncFactory.recordLogininfor(
                  username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
      throw new CaptchaException();
    }
  }

  /**
   * 记录登录信息
   *
   * @param userId 用户ID
   */
  public void recordLoginInfo(Long userId) {
    SysUser sysUser = new SysUser();
    sysUser.setUserId(userId);
    sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
    sysUser.setLoginDate(DateUtils.getNowDate());
    userService.updateUserProfile(sysUser);
  }
}
