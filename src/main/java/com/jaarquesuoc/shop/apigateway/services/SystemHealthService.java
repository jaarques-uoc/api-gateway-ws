package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.configuration.ServersProperties;
import com.jaarquesuoc.shop.apigateway.dtos.ServiceHealthDto;
import com.jaarquesuoc.shop.apigateway.dtos.SystemHealthDto;
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

    public SystemHealthDto checkSystemHealth() {
        SystemHealthDto systemHealthDto = SystemHealthDto.builder()
            .services(serversProperties.getHealthServers().stream()
                .map(this::checkServiceHealth)
                .collect(toList()))
            .build();

        systemHealthDto.getServices().stream()
            .filter(service -> service.getStatus() == DOWN)
            .findFirst()
            .ifPresent(dontCare -> serversProperties.getWakeupServers()
                .forEach(this::wakeUp));

        return systemHealthDto;
    }

    private ServiceHealthDto checkServiceHealth(final String url) {
        ServiceHealthDto serviceHealthDto;

        try {
            serviceHealthDto = healthClient.healthCheck(URI.create(url));
            serviceHealthDto.setUrl(url);
        } catch (Exception e) {
            serviceHealthDto = ServiceHealthDto.builder()
                .url(url)
                .status(DOWN)
                .build();
        }

        return serviceHealthDto;
    }

    private void wakeUp(final String url) {
        try {
            healthClient.healthCheck(URI.create(url));
        } catch (Exception e) {
        }
    }
}