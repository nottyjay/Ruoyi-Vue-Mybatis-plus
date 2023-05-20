package com.alphay.boot.web.security.handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.common.constant.Constants;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.domain.model.LoginUser;
import com.alphay.boot.common.utils.ServletUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson2.JSON;
import com.alphay.boot.web.manager.AsyncManager;
import com.alphay.boot.web.manager.factory.AsyncFactory;

/**
 * 自定义退出处理类 返回成功
 *
 * @author d3code
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
  @Autowired private TokenService tokenService;

  /**
   * 退出处理
   *
   * @return
   */
  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    LoginUser loginUser = tokenService.getLoginUser(request);
    if (StringUtils.isNotNull(loginUser)) {
      String userName = loginUser.getUsername();
      // 删除用户缓存记录
      tokenService.delLoginUser(loginUser.getToken());
      // 记录用户退出日志
      AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
    }
    ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
  }
}
