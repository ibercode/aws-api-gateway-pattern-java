package com.ibercode.sales.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibercode.sales.model.CustomerSale;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.UUID;

public class SalesDDBUtils {

    private final DynamoDbClient ddb = DynamoDbClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
            .build();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(ddb)
            .build();
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public String persistPayment(String body, String tableName) {

        DynamoDbTable<CustomerSale> mappedTable = enhancedClient
                .table(tableName, TableSchema.fromBean(CustomerSale.class));

        String saleId = UUID.randomUUID().toString();
        try {
            CustomerSale payment = MAPPER.readValue(body, CustomerSale.class);
            payment.setSaleId(saleId);
            mappedTable.putItem(payment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return saleId;
    }
}
