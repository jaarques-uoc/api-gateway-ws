package com.jaarquesuoc.shop.apigateway.controllers;

import com.jaarquesuoc.shop.apigateway.dtos.InitialisationDto;
import com.jaarquesuoc.shop.apigateway.services.InitialisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitialisationController {

    private final InitialisationService initialisationService;

    @GetMapping("/init")
    public List<InitialisationDto> initialise() {
        return initialisationService.initialiseSystem();
    }
}
