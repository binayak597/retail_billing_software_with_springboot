package com.rbs.retail.billing.services;


import com.razorpay.RazorpayException;
import com.rbs.retail.billing.response.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;

}
