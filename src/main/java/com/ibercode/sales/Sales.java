package com.ibercode.sales;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibercode.sales.utils.SalesDDBUtils;

public class Sales implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String region = System.getenv("REGION");
        String tableName = System.getenv("SALES_TABLE");

        SalesDDBUtils ddbUtils = new SalesDDBUtils(region);

        String salesId = ddbUtils.persistPayment(event.getBody(), tableName);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setBody(MAPPER.writeValueAsString(salesId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}