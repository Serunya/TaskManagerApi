package com.tailspin.taskmanager.model.mapper;

import com.tailspin.taskmanager.model.Message;
import com.tailspin.taskmanager.model.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
long id,
        long senderId,
        long chatId,
        String senderName,
        Date timestamp,
        String content
 */
@Component
public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(
                rs.getLong("id"),
                rs.getLong("senderId"),
                rs.getLong("chatId"),
                rs.getDate("timestamp"),
                rs.getString("content"));
    }
}
