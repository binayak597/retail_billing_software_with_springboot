package com.rbs.retail.billing.controllers;

import com.razorpay.RazorpayException;
import com.rbs.retail.billing.dto.PaymentDto;
import com.rbs.retail.billing.dto.PaymentVerificationDto;
import com.rbs.retail.billing.response.OrderResponse;
import com.rbs.retail.billing.response.RazorpayOrderResponse;
import com.rbs.retail.billing.services.OrderService;
import com.rbs.retail.billing.services.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<RazorpayOrderResponse> createRazorpayOrder(@RequestBody PaymentDto dto) throws RazorpayException {

        RazorpayOrderResponse response = razorpayService.createOrder(dto.getAmount(), dto.getCurrency());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/verify")
    public ResponseEntity<OrderResponse> verifyPayment(@RequestBody PaymentVerificationDto dto){

        OrderResponse response = orderService.verifyPayment(dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
