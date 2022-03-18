package com.ibercode.sales.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;

@DynamoDbBean
public class CustomerSale implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("saleId")
    private String saleId = null;
    @JsonProperty("customerId")
    private String customerId = null;
    @JsonProperty("product")
    private String product = null;
    @JsonProperty("amount")
    private String amount = null;

    public CustomerSale() {
    }

    public CustomerSale(String saleId, String customerId, String product, String amount) {
        this.saleId = saleId;
        this.customerId = customerId;
        this.product = product;
        this.amount = amount;
    }

    @DynamoDbPartitionKey
    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
