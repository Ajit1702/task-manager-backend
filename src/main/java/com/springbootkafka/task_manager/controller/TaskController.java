package com.springbootkafka.task_manager.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootkafka.task_manager.dto.ApiResponse;
import com.springbootkafka.task_manager.dto.TaskRequestDto;
import com.springbootkafka.task_manager.dto.TaskResponseDto;
import com.springbootkafka.task_manager.service.TaskService;



@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	@GetMapping
	public ResponseEntity<List<TaskResponseDto>> getUserTask(Authentication authentication){
		return ResponseEntity.ok(taskService.getTaskForUser(authentication.getName()));
	}
	
	@PostMapping
	public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto, Authentication authentication){
		return ResponseEntity.ok(taskService.createTask(authentication.getName(), taskRequestDto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto, Authentication authentication) throws AccessDeniedException{
		return ResponseEntity.ok(taskService.updateTask(id,authentication.getName(), taskRequestDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id, Authentication authentication) throws AccessDeniedException{
		taskService.deleteTask(id, authentication.getName());
		ApiResponse apiResponse = new ApiResponse("Task deleted successfully", id);
		return ResponseEntity.ok(apiResponse);
	}
	
}
