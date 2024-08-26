package com.tripmaven.api;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ApiService {

	private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
    }

    public String callFastAPIOCRService(Map map) {
        return this.webClient.post()
                .uri("/ocr")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("base64Encoded=" + map.get("imageData") + "&ocrValue="+map.get("type"))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 비동기 호출을 동기식으로 대기
    }

    public String callFastAPISTTService(String filePath) {
        return this.webClient.post()
                .uri("/stt")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("filepath=" + filePath)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 비동기 호출을 동기식으로 대기
    }

}
