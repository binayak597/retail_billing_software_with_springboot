package com.rbs.retail.billing.response;

import com.rbs.retail.billing.dto.PaymentMethod;
import com.rbs.retail.billing.entities.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private String orderId;

    private String customerName;

    private String phoneNumber;

    private List<OrderItemResponse> cartItems;

    private Double subTotal;

    private Double grandTotal;

    private Double tax;

    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    private PaymentDetails paymentDetails;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse{

        private String itemId;

        private String name;

        private Double price;

        private Integer quantity;

    }


}
