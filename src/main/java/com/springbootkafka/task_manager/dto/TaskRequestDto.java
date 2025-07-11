package com.springbootkafka.task_manager.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskRequestDto {
	private String title;
	private String description;
	private String status;
	private LocalDate dueDate;
	 private String username;
	 
	 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public TaskRequestDto(String title, String description, String status, LocalDate dueDate, String username) {
		super();
		this.title = title;
		this.description = description;
		this.status = status;
		this.dueDate = dueDate;
		this.username= username;
	}
	public TaskRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
