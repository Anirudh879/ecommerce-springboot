package com.codeWithProject.ecom.helper;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

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
    @Builder.Default
    private String traceId = MDC.get("traceId");
    @Builder.Default
    private String spanId = MDC.get("spanId");
}
