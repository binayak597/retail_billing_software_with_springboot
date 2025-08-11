package com.rbs.retail.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto{

    private String customerName;

    private String phoneNumber;

    private List<OrderItemDto> cartItems;

    private Double subTotal;

    private Double grandTotal;

    private Double tax;

    private PaymentMethod paymentMethod;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto{

        private String itemId;

        private String name;

        private Double price;

        private Integer quantity;

    }
}
