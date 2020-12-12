package com.technhongplus.sellengeapi;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
public class SellengeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellengeApiApplication.class, args);
    }

    @Bean
    public RestTemplate nhRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setMessageConverters(Collections.singletonList(jacksonConverter()));
        return restTemplate;
    }

    public MappingJackson2HttpMessageConverter jacksonConverter() {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.propertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(b.build());

        return converter;
    }

    public static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
