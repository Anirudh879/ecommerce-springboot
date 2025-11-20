package com.codeWithProject.ecom.helper;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;
	private String traceId;
	
	public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp, String traceId) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = timestamp;
		this.traceId = traceId;
	}
	
	public ApiResponse(boolean success, String message, T data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
		this.traceId = UUID.randomUUID().toString();
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
