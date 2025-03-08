package com.hanghae.orderservice.domain.order.client;

import com.hanghae.orderservice.domain.order.event.OrderCreatedEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

  @PostMapping("/internal/payment")
  void completePayment(@RequestBody OrderCreatedEvent event);

}
