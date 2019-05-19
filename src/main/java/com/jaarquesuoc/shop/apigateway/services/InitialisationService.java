package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.configuration.ServersProperties;
import com.jaarquesuoc.shop.apigateway.dtos.InitialisationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

import static com.jaarquesuoc.shop.apigateway.dtos.InitialisationDto.InitialisationStatus.KO;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitialisationService {

    private final InitialisationClient initialisationClient;

    private final ServersProperties serversProperties;

    public List<InitialisationDto> initialiseSystem() {
        return serversProperties.getHealthServers().stream()
            .map(this::initialiseService)
            .collect(toList());
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