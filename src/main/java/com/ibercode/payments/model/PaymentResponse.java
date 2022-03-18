package com.ibercode.payments.model;

public class PaymentResponse {

    private String paymentId;

    public PaymentResponse() {
    }

    public PaymentResponse(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
