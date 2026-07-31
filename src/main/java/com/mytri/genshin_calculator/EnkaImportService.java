package com.mytri.genshin_calculator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnkaImportService {

    private final RestTemplate restTemplate;

    public EnkaImportService() {
        org.springframework.http.client.SimpleClientHttpRequestFactory factory =
                new org.springframework.http.client.SimpleClientHttpRequestFactory();
        this.restTemplate = new RestTemplate(factory);
    }

    public EnkaResponse fetchEnkaResponse(String uid) {
        String url = "https://enka.network/api/uid/" + uid;
        return restTemplate.getForObject(url, EnkaResponse.class);
    }
}