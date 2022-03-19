package com.ibercode.sales;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibercode.sales.utils.SalesDDBUtils;

public class Sales implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String TABLE_NAME = System.getenv("SALES_TABLE");
    private final ObjectMapper MAPPER = new ObjectMapper();
    private final SalesDDBUtils ddbUtils = new SalesDDBUtils();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String salesId = ddbUtils.persistPayment(event.getBody(), TABLE_NAME);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setBody(MAPPER.writeValueAsString(salesId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}