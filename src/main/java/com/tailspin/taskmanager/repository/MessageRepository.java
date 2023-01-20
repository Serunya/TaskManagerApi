package com.tailspin.taskmanager.repository;

import com.tailspin.taskmanager.model.Message;
import com.tailspin.taskmanager.model.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS messages (" +
            "id SERIAl NOT NULL," +
            "senderId integer NOT NULL," +
            "chatId integer NOT NULL," +
            "timestamp timestamp NOT NULL," +
            "content text NOT NULL" +
            ");";

    private final String SQL_ADD_MESSAGE = "INSERT INTO messages (senderid,chatid,timestamp,content) VALUES (:senderid,:chatid,:timestamp,:content)";
    private final String SQL_GET_MESSAGE_BY_CHAT_ID = "SELECT * FROM messages WHERE chatid = :chatid";
    @Autowired
    private MessageMapper mapper;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MessageRepository(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute(CREATE_TABLE);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void addMessage(Message message){
        var param = new MapSqlParameterSource();
        param.addValue("senderid",message.senderId());
        param.addValue("chatid",message.chatId());
        param.addValue("timestamp",message.timestamp());
        param.addValue("content",message.content());
        jdbcTemplate.update(SQL_ADD_MESSAGE,param);
    }

    public List<Message> getMessageByChatId(int chatId){
        var param = new MapSqlParameterSource();
        param.addValue("chatid",chatId);
        return jdbcTemplate.query(SQL_GET_MESSAGE_BY_CHAT_ID,param,mapper);
    }
}
