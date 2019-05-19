package com.jaarquesuoc.shop.apigateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitialisationDto {

    public enum InitialisationStatus {OK, KO}

    private String url;

    private InitialisationStatus initialisationStatus;

    private Object metadata;
}
