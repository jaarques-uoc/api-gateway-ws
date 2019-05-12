package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.configuration.ServersProperties;
import com.jaarquesuoc.shop.apigateway.dtos.ServiceHealth;
import com.jaarquesuoc.shop.apigateway.dtos.SystemHealth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;

import static com.jaarquesuoc.shop.apigateway.dtos.HealthStatus.DOWN;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemHealthService {

    private final HealthClient healthClient;

    private final ServersProperties serversProperties;

    public SystemHealth checkSystemHealth() {
        return SystemHealth.builder()
            .services(serversProperties.getServers().stream()
                .map(this::checkServiceHealth)
                .collect(toList()))
            .build();
    }

    private ServiceHealth checkServiceHealth(final String url) {
        ServiceHealth serviceHealth;

        try {
            serviceHealth = healthClient.healthCheck(URI.create(url));
            serviceHealth.setUrl(url);
        } catch (Exception e) {
            serviceHealth = ServiceHealth.builder()
                .url(url)
                .status(DOWN)
                .build();
        }

        return serviceHealth;
    }
}