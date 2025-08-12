package com.rbs.retail.billing.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RazorpayOrderResponse {

    private String id;
    private String entity;
    private Integer amount;
    private String currency;
    private String status;
    private Date createdAt;
    private String receipt;
}
