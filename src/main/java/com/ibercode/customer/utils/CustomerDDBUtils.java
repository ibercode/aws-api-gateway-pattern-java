package com.ibercode.customer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibercode.customer.model.CustomerPayment;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.UUID;

public class CustomerDDBUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
                .build();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(ddb)
            .build();

    public String persistPayment(String body, String tableName) {
        DynamoDbTable<CustomerPayment> mappedTable = enhancedClient
                .table(tableName, TableSchema.fromBean(CustomerPayment.class));

        String paymentId = UUID.randomUUID().toString();
        try {
            CustomerPayment payment = MAPPER.readValue(body, CustomerPayment.class);
            payment.setPaymentId(paymentId);
            mappedTable.putItem(payment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return paymentId;
    }
}
