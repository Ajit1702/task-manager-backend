package com.springbootkafka.task_manager.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootkafka.task_manager.dto.TaskEventDto;
import com.springbootkafka.task_manager.dto.TaskRequestDto;
import com.springbootkafka.task_manager.dto.TaskResponseDto;
import com.springbootkafka.task_manager.exception.ResourceNotFoundException;
import com.springbootkafka.task_manager.model.Task;
import com.springbootkafka.task_manager.model.User;
import com.springbootkafka.task_manager.repository.TaskRepository;
import com.springbootkafka.task_manager.repository.UserRepository;


@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private KafkaProducerService kafkaProducer;

	public List<TaskResponseDto> getTaskForUser(String username) {
		List<Task> tasks = taskRepository.findByUserUsername(username);
		return tasks.stream().map(task -> {
			TaskResponseDto dto = modelMapper.map(task, TaskResponseDto.class);
			dto.setUsername(task.getUser().getUsername());
			return dto;
		}).collect(Collectors.toList());
	}

	public TaskResponseDto createTask(String username, TaskRequestDto taskRequestDto) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Task task = modelMapper.map(taskRequestDto, Task.class);
		task.setUser(user);
		Task save = taskRepository.save(task);
		 TaskEventDto eventDto = new TaskEventDto(
			        save.getId(), save.getTitle(), save.getStatus(), user.getUsername()
			    );
		 
			kafkaProducer.sendTaskEvent("TASK_CREATED", eventDto);
		TaskResponseDto responseDto = modelMapper.map(save, TaskResponseDto.class);
		responseDto.setUsername(user.getUsername());
		return responseDto;
	}

	public TaskResponseDto updateTask(Long id, String username, TaskRequestDto requestDto)
			throws AccessDeniedException {
		Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));
		if (!task.getUser().getUsername().equals(username))
			throw new AccessDeniedException("Unauthorized");
		task.setTitle(requestDto.getTitle());
		task.setDescription(requestDto.getDescription());
		task.setStatus(requestDto.getStatus());
		task.setDueDate(requestDto.getDueDate());
		Task updatedTask = taskRepository.save(task);
		 TaskEventDto eventDto = new TaskEventDto(
			        updatedTask.getId(), updatedTask.getTitle(), updatedTask.getStatus(), username
			    );
			    kafkaProducer.sendTaskEvent("TASK_UPDATED", eventDto);
		TaskResponseDto responseDto = modelMapper.map(updatedTask, TaskResponseDto.class);
		responseDto.setUsername(task.getUser().getUsername());
		return responseDto;
	}
	
	public void deleteTask(Long id, String username) throws AccessDeniedException {
		Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));
		if (!task.getUser().getUsername().equals(username))
			throw new AccessDeniedException("Unauthorized");
		
		TaskEventDto eventDto = new TaskEventDto(
		        task.getId(), task.getTitle(), task.getStatus(), username
		    );
		    kafkaProducer.sendTaskEvent("TASK_DELETED", eventDto);
		taskRepository.delete(task);
	}
}
