package com.tailspin.taskmanager.model.mapper;

import com.tailspin.taskmanager.model.user.UserName;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserNameMapper implements RowMapper<UserName> {
    @Override
    public UserName mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserName(rs.getInt("id"),rs.getString("firstName"),rs.getString("secondName"));
    }
}
