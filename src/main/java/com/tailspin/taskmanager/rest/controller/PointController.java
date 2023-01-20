package com.tailspin.taskmanager.rest.controller;

import com.tailspin.taskmanager.model.Point;
import com.tailspin.taskmanager.repository.PointsRepository;
import com.tailspin.taskmanager.rest.payload.requests.PointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/point/")
public class PointController {


    @Autowired
    PointsRepository repository;

    @PostMapping(path = "/addPoints")
    public ResponseEntity addPoints(@RequestBody PointsRequest points){
        for(PointsRequest.point point : points.getPoints()){
            repository.addPoint(point.toPoint());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/getPoints")
    public List<Point> getPoints(@RequestParam int taskId){
        return repository.getPointByTaskId(taskId);
    }




}
