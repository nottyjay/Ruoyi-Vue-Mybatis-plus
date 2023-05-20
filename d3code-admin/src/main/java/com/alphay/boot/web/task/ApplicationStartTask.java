package com.alphay.boot.web.task;

import com.alphay.boot.system.service.ISysConfigService;
import com.alphay.boot.system.service.ISysDictTypeService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统启动任务
 * 防止其他模块引用d3code-system框架时重复刷新缓存
 *
 * @Author Nottyjay
 * @Date 2023/03/29
 */
@Component
public class ApplicationStartTask implements ApplicationRunner {

  @Resource
  private ISysConfigService configService;
  @Resource
  private ISysDictTypeService dictTypeService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // 初始化系统配置到缓存
    configService.loadingConfigCache();
    // 初始化系统字典到缓存
    dictTypeService.loadingDictCache();
  }
}
