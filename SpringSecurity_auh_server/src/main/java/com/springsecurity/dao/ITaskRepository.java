package com.springsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.entities.Task;

public interface ITaskRepository extends JpaRepository<Task,Long>{

}
