package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.dtos.InitialisationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "initialisation", url = "https://this-is-a-placeholder.com")
public interface InitialisationClient {

    @GetMapping("/init")
    InitialisationDto initialise(URI uri);
}
