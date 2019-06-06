package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.configuration.ServersProperties;
import com.jaarquesuoc.shop.apigateway.dtos.ServiceHealthDto;
import com.jaarquesuoc.shop.apigateway.dtos.SystemHealthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.List;

import static com.jaarquesuoc.shop.apigateway.dtos.HealthStatus.DOWN;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemHealthService {

    private final HealthClient healthClient;

    private final ServersProperties serversProperties;

    private final Scheduler scheduler = Schedulers.newParallel("healthScheduler", 10);

    public SystemHealthDto checkSystemHealth() {
        List<ServiceHealthDto> serviceHealthDtos = checkParallelServiceHealth(serversProperties.getHealthServers())
            .collectList()
            .block();

        SystemHealthDto systemHealthDto = SystemHealthDto.builder()
            .services(serviceHealthDtos)
            .build();

        wakeUpDownServices(systemHealthDto);

        return systemHealthDto;
    }

    private Flux<ServiceHealthDto> checkParallelServiceHealth(final List<String> serverUrls) {
        return Flux.fromIterable(serverUrls)
            .flatMap(serverUrl -> Mono.defer(() -> Mono.just(checkServiceHealth(serverUrl)))
                .subscribeOn(scheduler));
    }

    private ServiceHealthDto checkServiceHealth(final String url) {
        ServiceHealthDto serviceHealthDto;

        try {
            Thread.sleep(10000);
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

    private void wakeUpDownServices(final SystemHealthDto systemHealthDto) {
        systemHealthDto.getServices().stream()
            .filter(service -> service.getStatus() == DOWN)
            .findFirst()
            .ifPresent(dontCare -> checkParallelServiceHealth(serversProperties.getWakeupServers())
                .subscribe());
    }
}