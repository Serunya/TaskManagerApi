package com.tailspin.taskmanager.repository;


import com.tailspin.taskmanager.model.Task;
import com.tailspin.taskmanager.model.mapper.TaskMapper;
import com.tailspin.taskmanager.model.mapper.IdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tasks(" +
            "id SERIAL NOT NULL," +
            "title text NOT NULL," +
            "description text NOT NULL," +
            "executorId integer NOT NULL," +
            "curratorId integer NOT NULL," +
            "deadLine date NOT NULL," +
            "startingDate date NOT NULL)";

    private final String SQL_ADD_TASK = "INSERT INTO tasks (title,description,executorId,curratorId,deadLine,startingDate)" +
            "VALUES (:title,:description,:executorId,:curratorId,:deadLine,:startingDate) RETURNING id";

    private final String SQL_GET_ALL_TASK = "SELECT * FROM tasks";

    private final String SQL_GET_TASK_BY_ID = "SELECT * FROM tasks WHERE id = :id";
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private IdMapper idMapper;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate){
        jdbcTemplate.execute(CREATE_TABLE);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public Integer addTask(Task task){
        var param  = new MapSqlParameterSource();
        param.addValue("title",task.title());
        param.addValue("description",task.description());
        param.addValue("executorId",task.executorId());
        param.addValue("curratorId",task.curratorId());
        param.addValue("deadLine",task.deadLine());
        param.addValue("startingDate",task.startingDate());
        return jdbcTemplate.query(SQL_ADD_TASK,param,idMapper).stream().findFirst().orElse(null);
    }


    public List<Task> getAllTask(){
        return jdbcTemplate.query(SQL_GET_ALL_TASK,taskMapper);
    }

    public Task getTaskById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return  jdbcTemplate.query(SQL_GET_TASK_BY_ID,param,taskMapper).stream().findFirst().orElse(null);
    }
}
