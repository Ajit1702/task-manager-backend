package com.springbootkafka.task_manager.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskResponseDto {
	private Long id;
	private String title;
	private String description;
	private String status;
	private LocalDate dueDate;
	private String username;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public TaskResponseDto(Long id, String title, String description, String status, LocalDate dueDate,
			String username) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.dueDate = dueDate;
		this.username = username;
	}
	public TaskResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
