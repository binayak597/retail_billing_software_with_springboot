package com.rbs.retail.billing.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Double todaySales;

    private Long todayOrdersCount;

    private List<OrderResponse> recentOrders;
}
