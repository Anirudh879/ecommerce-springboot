package com.codeWithProject.ecom.helper;


public class ResponseHelper {
	
	
	public static <T> ApiResponse<T>  success(String message,T data) {
		return new ApiResponse<T>(true, message, data);
	}
	
	
	public static <T> ApiResponse<T> failure(String message,T data){
		return new ApiResponse<T>(false, message, data);
	}
	
	public static <T> ApiResponse<T> FailureAnalysis(String message,T data){
		return new ApiResponse<T>(false, message,null);
	}
	
}
