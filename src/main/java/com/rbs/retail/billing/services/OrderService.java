package com.rbs.retail.billing.services;

import com.rbs.retail.billing.dto.OrderDto;
import com.rbs.retail.billing.dto.PaymentVerificationDto;
import com.rbs.retail.billing.response.OrderResponse;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderDto request);

    void deleteOrder(String orderId);

    List<OrderResponse> getAllLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationDto dto);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();
}
