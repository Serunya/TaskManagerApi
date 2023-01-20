package com.tailspin.taskmanager.model.mapper;

import com.tailspin.taskmanager.model.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


/*
 "id SERIAL NOT NULL," +
            "title text NOT NULL," +
            "description text NOT NULL," +
            "executorId integer NOT NULL," +
            "curratorId integer NOT NULL," +
            "deadLine date NOT NULL," +
            "startingDate date NOT NULL)";
 */
@Component
public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("executorid"),
                rs.getInt("curratorid"),
                rs.getDate("deadline"),
                rs.getDate("startingdate")
        );
    }
}
