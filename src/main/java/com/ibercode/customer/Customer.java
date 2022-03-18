package com.ibercode.customer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibercode.customer.utils.CustomerDDBUtils;

public class Customer implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String region = System.getenv("REGION");
        String tableName = System.getenv("CUSTOMERS_TABLE");


        CustomerDDBUtils ddbUtils = new CustomerDDBUtils(region);

        String paymentId = ddbUtils.persistPayment(event.getBody(), tableName);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setBody(MAPPER.writeValueAsString(paymentId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}
