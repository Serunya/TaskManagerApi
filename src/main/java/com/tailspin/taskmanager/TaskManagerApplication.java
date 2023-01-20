package com.tailspin.taskmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class TaskManagerApplication {



    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

}
