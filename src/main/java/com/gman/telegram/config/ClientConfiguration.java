package com.gman.telegram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gman.telegram.client.ApiClient;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Anton Mikhaylov on 05/10/2018.
 */
@Configuration
@AllArgsConstructor
public class ClientConfiguration {

    private ObjectMapper mapper;

    private ClientConfigProperties properties;

    @Bean
    public ApiClient getPikApiClient() {

        return Feign.builder()
                .encoder(new JacksonEncoder(mapper))
                .decoder(new JacksonDecoder(mapper))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(ApiClient.class, properties.getServer());
    }
}
