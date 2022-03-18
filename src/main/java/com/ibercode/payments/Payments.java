package com.ibercode.payments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.ibercode.payments.model.PaymentResponse;
import com.ibercode.payments.utils.PaymentUtils;

public class Payments implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final PaymentUtils paymentUtils = new PaymentUtils();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String commonUrl = System.getenv("COMMON_URL");

        String customerResponse = paymentUtils.updateCustomerDDB(event.getBody(),commonUrl + "/customer");
        paymentUtils.updateSalesDDB(event.getBody(),commonUrl + "/sales");
        paymentUtils.notifyCustomer(event.getBody(),commonUrl + "/communication");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        PaymentResponse paymentResponse = new PaymentResponse(customerResponse);

        gson.toJson(customerResponse);
        response.setBody(gson.toJson(paymentResponse));

        return response;
    }
}
