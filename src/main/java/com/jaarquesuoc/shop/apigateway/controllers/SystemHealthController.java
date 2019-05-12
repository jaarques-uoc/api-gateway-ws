package com.jaarquesuoc.shop.apigateway.controllers;

import com.jaarquesuoc.shop.apigateway.dtos.SystemHealth;
import com.jaarquesuoc.shop.apigateway.services.SystemHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemHealthController {

    private final SystemHealthService systemHealthService;

    @GetMapping("/system/health")
    public SystemHealth systemHealth() {
        return systemHealthService.checkSystemHealth();
    }
}
