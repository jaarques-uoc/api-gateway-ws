package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.configuration.ServersProperties;
import com.jaarquesuoc.shop.apigateway.dtos.InitialisationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.List;

import static com.jaarquesuoc.shop.apigateway.dtos.InitialisationDto.InitialisationStatus.KO;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitialisationService {

    private final InitialisationClient initialisationClient;

    private final ServersProperties serversProperties;

    private final Scheduler scheduler = Schedulers.newParallel("initScheduler", 10);

    public List<InitialisationDto> initialiseSystem() {
        return initialiseParallelServices(serversProperties.getInitServers())
                .collectList()
                .block();
    }

    private Flux<InitialisationDto> initialiseParallelServices(final List<String> serverUrls) {
        return Flux.fromIterable(serverUrls)
                .flatMap(serverUrl -> Mono.defer(() -> Mono.just(initialiseService(serverUrl)))
                        .subscribeOn(scheduler));
    }

    private InitialisationDto initialiseService(final String url) {
        InitialisationDto initialisationDto;

        try {
            initialisationDto = initialisationClient.initialise(URI.create(url));
            initialisationDto.setUrl(url);
        } catch (Exception e) {
            initialisationDto = InitialisationDto.builder()
                    .url(url)
                    .initialisationStatus(KO)
                    .build();
        }

        return initialisationDto;
    }
}
