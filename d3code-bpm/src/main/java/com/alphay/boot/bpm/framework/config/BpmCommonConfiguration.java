package com.alphay.boot.bpm.framework.config;

import com.alphay.boot.bpm.framework.event.BpmProcessInstanceResultEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bpm 通用 configuration 配置类，
 *
 * @author Nottyjay
 */
@Configuration(proxyBeanMethods = false)
public class BpmCommonConfiguration {

  @Bean
  public BpmProcessInstanceResultEventPublisher processInstanceResultEventPublisher(
      ApplicationEventPublisher publisher) {
    return new BpmProcessInstanceResultEventPublisher(publisher);
  }
}
