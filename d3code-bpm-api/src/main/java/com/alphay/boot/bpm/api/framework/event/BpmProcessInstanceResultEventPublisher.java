package com.alphay.boot.bpm.api.framework.event;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;

/**
 * {@link BpmProcessInstanceResultEvent} 的生产者
 *
 * @author Nottyjay
 */
@AllArgsConstructor
@Validated
public class BpmProcessInstanceResultEventPublisher {

  private final ApplicationEventPublisher publisher;

  public void sendProcessInstanceResultEvent(@Valid BpmProcessInstanceResultEvent event) {
    publisher.publishEvent(event);
  }
}
