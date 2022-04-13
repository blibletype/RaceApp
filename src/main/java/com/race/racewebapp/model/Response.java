package com.race.racewebapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class Response {
    protected String raceStatus;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String delevoperMessage;
    protected List<?> data;
    protected LocalDateTime timeStamp;
    protected String token;

}
