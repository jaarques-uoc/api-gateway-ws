package com.jaarquesuoc.shop.apigateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties
@Data
public class ServersProperties {

    private Servers servers;

    private String edgeHostname;

    @Data
    public class Servers {

        private List<String> health;

        private List<String> wakeup;
    }
}
