package com.alphay.boot.web.controller;

import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultController extends BaseController {

  @RequestMapping("/bpm/**")
  public AjaxResult defaultBpm() {
    return AjaxResult.error("模块暂未启用！请先启用模块");
  }
}
