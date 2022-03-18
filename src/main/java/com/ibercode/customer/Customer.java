package com.ibercode.customer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibercode.customer.utils.CustomerDDBUtils;

public class Customer implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String TABLE_NAME = System.getenv("CUSTOMERS_TABLE");

    private final ObjectMapper MAPPER = new ObjectMapper();
    private final CustomerDDBUtils ddbUtils = new CustomerDDBUtils();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String paymentId = ddbUtils.persistPayment(event.getBody(), TABLE_NAME);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setBody(MAPPER.writeValueAsString(paymentId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}
