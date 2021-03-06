package com.jaarquesuoc.shop.apigateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties
@Data
public class ServersProperties {

    private List<String> healthServers;

    private List<String> wakeupServers;

    private List<String> initServers;

    private String edgeHostname;
}
