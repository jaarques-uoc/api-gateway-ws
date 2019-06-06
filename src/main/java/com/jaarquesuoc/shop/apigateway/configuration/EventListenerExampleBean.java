package com.jaarquesuoc.shop.apigateway.configuration;

import com.jaarquesuoc.shop.apigateway.services.SystemHealthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventListenerExampleBean {

    private final SystemHealthService systemHealthService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Starting system healthcheck.");
        log.info("{}", systemHealthService.checkSystemHealth());
    }
}
