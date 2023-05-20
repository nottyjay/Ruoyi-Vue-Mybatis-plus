package com.d3code.web.service;

import javax.annotation.Resource;

import com.d3code.security.service.TokenService;
import com.d3code.web.manager.AsyncManager;
import com.d3code.web.manager.factory.AsyncFactory;
import com.d3code.web.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.d3code.common.constant.CacheConstants;
import com.d3code.common.constant.Constants;
import com.d3code.common.core.domain.entity.SysUser;
import com.d3code.common.core.domain.model.LoginUser;
import com.d3code.common.core.redis.RedisCache;
import com.d3code.common.exception.ServiceException;
import com.d3code.common.exception.user.CaptchaException;
import com.d3code.common.exception.user.CaptchaExpireException;
import com.d3code.common.exception.user.UserPasswordNotMatchException;
import com.d3code.common.utils.DateUtils;
import com.d3code.common.utils.MessageUtils;
import com.d3code.common.utils.ServletUtils;
import com.d3code.common.utils.StringUtils;
import com.d3code.common.utils.ip.IpUtils;
import com.d3code.system.service.ISysConfigService;
import com.d3code.system.service.ISysUserService;

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
