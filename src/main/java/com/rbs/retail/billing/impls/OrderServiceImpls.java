package com.rbs.retail.billing.impls;

import com.rbs.retail.billing.dto.OrderDto;
import com.rbs.retail.billing.dto.PaymentMethod;
import com.rbs.retail.billing.dto.PaymentVerificationDto;
import com.rbs.retail.billing.entities.OrderEntity;
import com.rbs.retail.billing.entities.OrderItemEntity;
import com.rbs.retail.billing.entities.PaymentDetails;
import com.rbs.retail.billing.repositories.OrderRepository;
import com.rbs.retail.billing.response.OrderResponse;
import com.rbs.retail.billing.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpls implements OrderService{

    private final OrderRepository orderRepository;


    @Override
    public OrderResponse createOrder(OrderDto request) {

        OrderEntity newOrder = convertToOrderEntity(request);

        PaymentDetails paymentDetails = new PaymentDetails();

        paymentDetails.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH ?
                PaymentDetails.PaymentStatus.COMPLETED: PaymentDetails.PaymentStatus.PENDING);

        newOrder.setPaymentDetails(paymentDetails);

        List<OrderItemEntity> orderItems = request.getCartItems().stream()
                .map(this::conertToOrderItemEntity)
                .collect(Collectors.toList());

        newOrder.setItems(orderItems);

        newOrder = orderRepository.save(newOrder);

        return convertToOrderResponse(newOrder);
    }

    private OrderResponse convertToOrderResponse(OrderEntity newOrder) {

        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subTotal(newOrder.getSubTotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .cartItems(newOrder.getItems().stream()
                        .map(this::convertToOrderItemResponse)
                        .collect((Collectors.toList())))
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();
    }

    private OrderResponse.OrderItemResponse convertToOrderItemResponse(OrderItemEntity orderItemEntity) {

        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();

    }

    private OrderItemEntity conertToOrderItemEntity(OrderDto.OrderItemDto request) {

        return OrderItemEntity.builder()
                .itemId(request.getItemId())
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

    }

    private OrderEntity convertToOrderEntity(OrderDto request) {

        return OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subTotal(request.getSubTotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(request.getPaymentMethod())
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {

        OrderEntity existingOrder = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderRepository.delete(existingOrder);
    }

    @Override
    public List<OrderResponse> getAllLatestOrders() {

        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationDto request) {

        OrderEntity existingOrder = orderRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(!verifyRazorpaySignature(request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(), request.getRazorpaySignature())){

            throw new RuntimeException("Payment verification failed");
        }

        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();

        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);

        existingOrder = orderRepository.save(existingOrder);

        return convertToOrderResponse(existingOrder);
    }

    //TODO
    //not recommended for production
    //in production we need to many steps
    private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {

        return true;
    }
}