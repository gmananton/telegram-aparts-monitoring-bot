package com.gman.telegram.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Anton Mikhaylov on 23/10/2018.
 */
@ConfigurationProperties(prefix = "pik-api")
@Configuration
@Getter @Setter
public class ClientConfigProperties {

    private String server;
}
