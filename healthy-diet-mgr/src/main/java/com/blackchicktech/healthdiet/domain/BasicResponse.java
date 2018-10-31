package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown =true)
public class BasicResponse {

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("errorMsg")
    private String errorMsg;
}
