package com.jaarquesuoc.shop.apigateway.services;

import com.jaarquesuoc.shop.apigateway.dtos.ServiceHealthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "health", url = "https://this-is-a-placeholder.com")
public interface HealthClient {

    @GetMapping("/actuator/health")
    ServiceHealthDto healthCheck(URI uri);
}
