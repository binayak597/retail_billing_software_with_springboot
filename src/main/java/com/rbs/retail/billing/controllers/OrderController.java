package com.rbs.retail.billing.controllers;

import com.rbs.retail.billing.dto.OrderDto;
import com.rbs.retail.billing.response.OrderResponse;
import com.rbs.retail.billing.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createdOrder(@RequestBody OrderDto dto){

        OrderResponse data = orderService.createOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId){

        orderService.deleteOrder(orderId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<List<OrderResponse>> getLatestOrders(){

        List<OrderResponse> orders = orderService.getAllLatestOrders();

        return ResponseEntity.status(HttpStatus.OK).body(orders);

    }
}
