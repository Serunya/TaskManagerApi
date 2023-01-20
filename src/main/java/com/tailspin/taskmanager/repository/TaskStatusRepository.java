package com.tailspin.taskmanager.repository;

import com.tailspin.taskmanager.model.TaskStatus;
import com.tailspin.taskmanager.model.mapper.TaskStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TaskStatusRepository {

    private final String CREATE_TABLE = "create table if not exists taskstatus(" +
            "id Serial NOT NULL," +
            "taskId integer NOT NULL," +
            "status text NOT NULL)";

    private final String SQL_ADD_TASK_STATUS = "INSERT INTO taskstatus (taskId,status) VALUES (:taskId,:status)";
    private final String SQL_UPDATE_TASK_STATUS = "UPDATE taskstatus SET status=:status WHERE taskId = :taskId";
    private final String SQL_GET_ALL_TASK = "SELECT * FROM taskstatus";
    private final String SQL_GET_TASK_BY_ID = "SELECT * FROM taskstatus WHERE taskId = :taskId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private TaskStatusMapper mapper;

    public TaskStatusRepository(JdbcTemplate jdbcTemplate){
        jdbcTemplate.execute(CREATE_TABLE);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public void addTaskStatus(TaskStatus status){
        var param = new MapSqlParameterSource();
        param.addValue("taskId",status.taskId());
        param.addValue("status",status.status());
        jdbcTemplate.update(SQL_ADD_TASK_STATUS,param);
    }

    public void updateTaskStatus(int taskId, String status){
        var param = new MapSqlParameterSource();
        param.addValue("taskId",taskId);
        param.addValue("status",status);
        jdbcTemplate.update(SQL_UPDATE_TASK_STATUS,param);
    }


    public List<TaskStatus> getAllTask(){
        var param = new MapSqlParameterSource();
        return jdbcTemplate.query(SQL_GET_ALL_TASK,param,mapper);
    }


    public TaskStatus getTaskByTaskId(int id){
        var param = new MapSqlParameterSource();
        param.addValue("taskId", id);
        return jdbcTemplate.query(SQL_GET_TASK_BY_ID,param,mapper).stream().findFirst().orElse(null);
    }

}
