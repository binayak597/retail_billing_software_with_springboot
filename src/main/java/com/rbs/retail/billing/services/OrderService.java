package com.rbs.retail.billing.services;

import com.rbs.retail.billing.dto.OrderDto;
import com.rbs.retail.billing.dto.PaymentVerificationDto;
import com.rbs.retail.billing.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderDto request);

    void deleteOrder(String orderId);

    List<OrderResponse> getAllLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationDto dto);
}
