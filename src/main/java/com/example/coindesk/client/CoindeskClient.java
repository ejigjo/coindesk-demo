package com.example.coindesk.client;

import com.example.coindesk.dto.CoindeskResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** 負責呼叫外部 coindesk API */
@Component
public class CoindeskClient {

    private final RestTemplate restTemplate;
    private final String url;

    public CoindeskClient(RestTemplate restTemplate, @Value("${coindesk.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public CoindeskResponse fetch() {
        return restTemplate.getForObject(url, CoindeskResponse.class);
    }
}
