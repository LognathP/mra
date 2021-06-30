package com.mra.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Response {
	
	public HttpStatus status;
	public Object data;
}
