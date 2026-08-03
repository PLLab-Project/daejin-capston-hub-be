package com.daejin.capstone.global.config;

import java.net.http.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient restClient() {
    HttpClient httpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build();

    return RestClient.builder()
        .baseUrl("https://www.daejin.ac.kr")
        .requestFactory(new JdkClientHttpRequestFactory(httpClient))
        .build();
  }

}
