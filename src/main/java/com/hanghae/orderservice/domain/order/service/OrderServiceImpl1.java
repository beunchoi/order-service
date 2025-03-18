package com.hanghae.orderservice.domain.order.service;

import com.hanghae.orderservice.common.dto.ProductResponseDto;
import com.hanghae.orderservice.domain.order.dto.OrderRequestDto;
import com.hanghae.orderservice.domain.order.dto.OrderResponseDto;
import com.hanghae.orderservice.domain.order.dto.ReturnResponseDto;
import com.hanghae.orderservice.domain.order.entity.Order;
import com.hanghae.orderservice.domain.order.event.OrderCreatedEvent;
import com.hanghae.orderservice.domain.order.event.PaymentFailedEvent;
import com.hanghae.orderservice.domain.order.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Primary
public class OrderServiceImpl1 implements OrderService {

  private final OrderRepository orderRepository;
  private final PaymentEventService paymentEventService;
  private final ProductFetchService productFetchService;

  @Override
  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto requestDto, String productId, String userId) {
    // 상품 재고 체크
    ProductResponseDto product = checkProduct(productId);
    if (product.getStock() < requestDto.getQuantity()) {
      throw new IllegalArgumentException("상품 재고가 부족합니다.");
    }

    // 구매 가능 시간 체크

    // 주문 데이터 저장
    Order savedOrder = orderRepository.save(new Order(requestDto, userId, product));
    int totalPrice = savedOrder.getPrice() * savedOrder.getQuantity();

    paymentEventService.publish(new OrderCreatedEvent(savedOrder.getOrderId(),
        requestDto.getPaymentMethodId(), totalPrice,
        savedOrder.getProductId(), savedOrder.getQuantity()));

    return new OrderResponseDto(savedOrder);
  }

  @Override
  public ProductResponseDto checkProduct(String productId) {
    ProductResponseDto product = productFetchService.getProductByProductId(productId);

    if (product == null) {
      throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
    }

    return product;
  }

  @Override
  public List<OrderResponseDto> getOrdersByUserId(String userId) {
    return null;
  }

  @Override
  public OrderResponseDto cancelOrder(String userId, String orderId) {
    return null;
  }

  @Override
  public ReturnResponseDto requestProductReturn(String userId, String orderId) {
    return null;
  }

  @Override
  public void rollbackOrder(PaymentFailedEvent event) {

  }

  @Override
  public Order validateAndGetOrder(String userId, String orderId) {
    return null;
  }

  @Override
  public Order findOrderByOrderId(String orderId) {
    return null;
  }
}
