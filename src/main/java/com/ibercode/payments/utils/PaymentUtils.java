package com.ibercode.payments.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PaymentUtils {

    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public String updateCustomerDDB(String body, String customerUrl) {
        return getResponseBody(body, customerUrl);
    }

    public String updateSalesDDB(String body, String salesUrl) {
        return getResponseBody(body, salesUrl);
    }

    public String notifyCustomer(String body, String communicationUrl) {
        return getResponseBody(body, communicationUrl);
    }

    private String getResponseBody(String body, String url) {
        String responseBody = "";

        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .version(HttpClient.Version.HTTP_2)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBody = response.body().replace("\"","");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return responseBody;
    }
}
