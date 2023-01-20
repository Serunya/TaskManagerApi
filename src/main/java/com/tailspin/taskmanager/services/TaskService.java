package com.tailspin.taskmanager.services;


import com.tailspin.taskmanager.repository.TaskRepository;
import com.tailspin.taskmanager.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;



}
