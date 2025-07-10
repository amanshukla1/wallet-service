package com.aman.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ApiResponse {
	
	private int status;
	private String message;
	private LocalDateTime localDateTime;
	
	public ApiResponse(int status, String message) {
		this.status = status;
		this.message = message;
		this.localDateTime = LocalDateTime.now();
	}

}
