package com.alphay.boot.bpm.filter;

import com.alphay.boot.bpm.utils.FlowableUtil;
import com.alphay.boot.common.core.domain.model.LoginUser;
import com.alphay.boot.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FlowableWebFilter extends OncePerRequestFilter {

  @Autowired private TokenService tokenService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      // 设置工作流的用户
      LoginUser loginUser = tokenService.getLoginUser(request);
      if (loginUser != null) {
        FlowableUtil.setAuthenticatedUserId(loginUser.getUserId());
      }
      // 过滤
      filterChain.doFilter(request, response);
    } finally {
      // 清理
      FlowableUtil.clearAuthenticatedUserId();
    }
  }
}
