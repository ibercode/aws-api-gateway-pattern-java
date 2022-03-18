package com.ibercode.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;

@DynamoDbBean
public class CustomerPayment implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("paymentId")
    private String paymentId = null;
    @JsonProperty("customerId")
    private String customerId = null;
    @JsonProperty("customerFullName")
    private String customerFullName = null;
    @JsonProperty("customerEmail")
    private String customerEmail = null;
    @JsonProperty("amount")
    private String amount = null;
    @JsonProperty("product")
    private String product = null;

    public CustomerPayment() {
    }

    public CustomerPayment(String paymentId, String customerId, String customerFullName, String customerEmail, String amount, String product) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.customerFullName = customerFullName;
        this.customerEmail = customerEmail;
        this.amount = amount;
        this.product = product;
    }

    @DynamoDbPartitionKey
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CustomerPayment{" +
                "paymentId='" + paymentId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerFullName='" + customerFullName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", amount='" + amount + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}

