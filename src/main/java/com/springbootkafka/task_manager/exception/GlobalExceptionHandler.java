package com.springbootkafka.task_manager.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException exception) {
	        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage());
	    }

	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
	        return buildErrorResponse(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage());
	    }

	    @ExceptionHandler(UserAlreadyExistsException.class)
	    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
	        return buildErrorResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage());
	    }

	    // 🧹 Generic fallback (optional)
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
	        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage());
	    }
	    
	    @ExceptionHandler(JwtException.class)
	    public ResponseEntity<Map<String, Object>> handleJwtException(JwtException ex) {
	        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", "Invalid or expired JWT token: " + ex.getMessage());
	    }


	private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", error);
		body.put("message", message);
		return new ResponseEntity<>(body, status);
	}
}
