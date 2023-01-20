package com.tailspin.taskmanager.model.mapper;

import com.tailspin.taskmanager.model.RefreshToken;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RefreshTokenMapper implements RowMapper<RefreshToken> {
    @Override
    public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RefreshToken(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("token"),
                rs.getTimestamp("expiryDate")
        );
    }
}
