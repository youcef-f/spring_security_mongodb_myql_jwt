package com.springsecurity.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.dao.ITaskRepository;
import com.springsecurity.entities.Task;

@RestController
public class TaskRestController {

	@Autowired
	private ITaskRepository taskRepository;

	@GetMapping("/tasks")
	public List<Task> listTasks() {
		return taskRepository.findAll();
	}

	@PostMapping("/tasks")
	public Task save(@RequestBody Task task) {
		return taskRepository.save(task);
	}
	
	
}
