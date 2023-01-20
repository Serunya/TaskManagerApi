package com.tailspin.taskmanager.rest.controller;


import com.tailspin.taskmanager.model.Task;
import com.tailspin.taskmanager.model.TaskStatus;
import com.tailspin.taskmanager.model.user.User;
import com.tailspin.taskmanager.repository.TaskRepository;
import com.tailspin.taskmanager.repository.TaskStatusRepository;
import com.tailspin.taskmanager.repository.UserRepository;
import com.tailspin.taskmanager.rest.payload.requests.TaskRequest;
import com.tailspin.taskmanager.rest.payload.response.TaskIdResponte;
import com.tailspin.taskmanager.rest.payload.response.TaskResponse;
import com.tailspin.taskmanager.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/task/")
public class TaskController {

    @Autowired
    TaskRepository repository;

    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;


    @PostMapping(path = "/add")
    public ResponseEntity<TaskIdResponte> addTask(@RequestHeader("Authorization") String token, @RequestBody TaskRequest taskRequest){
        String login = jwtUtils.getLoginFromJwtToken(token.substring(7,token.length()));
        User user = userRepository.getByLogin(login);
        Task task = taskRequest.toTask((int) user.id());
        if(task != null) {
            Integer taskId = repository.addTask(task);
            taskStatusRepository.addTaskStatus(new TaskStatus(-1,taskId,"TO DO"));
            return ResponseEntity.ok(new TaskIdResponte(taskId));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping(path = "/getAllTask")
    public ResponseEntity getAllTask(){
        List<Task> tasks = repository.getAllTask();
        List<TaskStatus> taskStatuses = taskStatusRepository.getAllTask();
        ArrayList<TaskResponse> arrayList = new ArrayList<>();
        for(int i = 0; i < tasks.size();i++){
            arrayList.add(new TaskResponse(tasks.get(i),taskStatuses.get(i).status()));
        }
        return ResponseEntity.ok(arrayList);
    }
    @GetMapping(path = "/getTaskById")
    public ResponseEntity getTaskById(@RequestParam int id){
        Task task = repository.getTaskById(id);
        if(task != null) {
            TaskStatus taskStatus = taskStatusRepository.getTaskByTaskId(task.id());
            return ResponseEntity.ok(new TaskResponse(task, taskStatus.status()));
        }
        else
            return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping(path = "/upgradeStatus")
    public ResponseEntity upgradeStatus(@RequestHeader("Authorization") String token,@RequestParam int taskId){
        String login = jwtUtils.getLoginFromJwtToken(token.substring(7,token.length()));
        User user = userRepository.getByLogin(login);
        Task task = repository.getTaskById(taskId);
        if(task.executorId() != user.id()){
            return ResponseEntity.badRequest().build();
        }
        switch(taskStatusRepository.getTaskByTaskId(taskId).status()){
            case("TO DO") -> {taskStatusRepository.updateTaskStatus(taskId,"In Progress");}
            case("In Progress") -> {taskStatusRepository.updateTaskStatus(taskId,"Done");}
            default -> {return ResponseEntity.unprocessableEntity().build();}
        };
        return ResponseEntity.ok().build();
    }


}
