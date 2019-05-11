package com.jaarquesuoc.shop.apigateway.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceHealth {

    private String name;

    private String url;

    private HealthStatus status;
}
