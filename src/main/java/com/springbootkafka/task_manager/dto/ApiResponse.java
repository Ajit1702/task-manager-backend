package com.springbootkafka.task_manager.dto;
public class ApiResponse {
    private String message;
    private Long taskId;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public ApiResponse(String message, Long taskId) {
		super();
		this.message = message;
		this.taskId = taskId;
	}
    
    
}