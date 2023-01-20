package com.tailspin.taskmanager.model.mapper;

import com.tailspin.taskmanager.model.Point;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PointMapper implements RowMapper<Point> {
    @Override
    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Point(rs.getInt("id"),
                rs.getString("content"),
                rs.getBoolean("isDone"),
                rs.getInt("taskid"));
    }
}
