package com.tailspin.taskmanager.model.mapper;

import com.tailspin.taskmanager.model.TaskStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskStatusMapper implements RowMapper<TaskStatus> {

    @Override
    public TaskStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TaskStatus(rs.getInt("id"),
                rs.getInt("taskId"),
                rs.getString("status"));
    }
}
