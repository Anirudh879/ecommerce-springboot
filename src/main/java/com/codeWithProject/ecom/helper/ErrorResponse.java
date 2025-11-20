package com.codeWithProject.ecom.helper;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
	private boolean success;
    private String errorCode;      // UNIQUE error code
    private String message;        // Short error message
    private String details;        // Exception message or validation errors
    private String path;           // URL that caused error
    @Builder.Default
    private Instant timestamp = Instant.now();
    private String traceId;
}
