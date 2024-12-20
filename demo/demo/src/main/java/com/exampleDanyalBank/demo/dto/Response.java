package com.exampleDanyalBank.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String responseCode;
    private String responseMessage;
    private Data data;

}
