package com.springbootkafka.task_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootkafka.task_manager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	 List<Task> findByUserUsername(String username);
}
