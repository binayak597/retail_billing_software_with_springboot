package com.rbs.retail.billing.controllers;

import com.rbs.retail.billing.response.DashboardResponse;
import com.rbs.retail.billing.response.OrderResponse;
import com.rbs.retail.billing.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardData() {

        LocalDate today = LocalDate.now();

        Double todaySale = orderService.sumSalesByDate(today);
        Long todayOrdersCount = orderService.countByOrderDate(today);
        List<OrderResponse> recentOrders = orderService.findRecentOrders();

        DashboardResponse response = new DashboardResponse();

        response.setTodaySales(todaySale != null ? todaySale: 0.0);
        response.setTodayOrdersCount(todayOrdersCount != null? todayOrdersCount: 0);
        response.setRecentOrders(recentOrders);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
