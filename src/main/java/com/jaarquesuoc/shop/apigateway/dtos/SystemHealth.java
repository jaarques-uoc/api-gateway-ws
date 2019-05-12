package com.jaarquesuoc.shop.apigateway.dtos;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SystemHealth {
    private List<ServiceHealth> services;
}
