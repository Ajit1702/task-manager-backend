package com.springbootkafka.task_manager.dto;

public class TaskEventDto {
	private Long id;
	private String title;
	private String status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TaskEventDto(Long id, String title, String status, String username) {
		super();
		this.id = id;
		this.title = title;
		this.status = status;
		this.username = username;
	}

	public TaskEventDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
