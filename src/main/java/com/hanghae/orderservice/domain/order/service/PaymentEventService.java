package com.hanghae.orderservice.domain.order.service;

import com.hanghae.orderservice.domain.order.client.PaymentServiceClient;
import com.hanghae.orderservice.domain.order.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventService {

  private final PaymentServiceClient paymentServiceClient;

  public void publish(OrderCreatedEvent event) {
    paymentServiceClient.completePayment(event);
  }

}
