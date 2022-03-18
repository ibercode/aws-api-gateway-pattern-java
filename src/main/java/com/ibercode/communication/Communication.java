package com.ibercode.communication;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

public class Communication implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String region = System.getenv("REGION");
        String topicARN = System.getenv("TOPIC_ARN");

        String snsResponse = publishMessage(event.getBody(), region, topicARN);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setBody(MAPPER.writeValueAsString(snsResponse));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String publishMessage(String message, String region, String topicARN){

        String response = "";
        SnsClient snsClient = SnsClient.builder()
                .region(Region.of(region))
                .build();

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(topicARN)
                    .build();

            PublishResponse result = snsClient.publish(request);
            response = result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode();

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return response;
    }
}
