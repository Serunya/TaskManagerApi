package com.tailspin.taskmanager.repository;

import com.tailspin.taskmanager.model.mapper.UserNameMapper;
import com.tailspin.taskmanager.model.user.User;
import com.tailspin.taskmanager.model.mapper.UserMapper;
import com.tailspin.taskmanager.model.user.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id SERIAl NOT NULL," +
            "login text NOT NULL," +
            "password text NOT NULL," +
            "firstName text NOT NULL," +
            "secondName text NOT NULL" +
            ");";
    private final String SQL_GET_USER_BY_LOGIN = "SELECT * from users where login = :login";
    private final String SQL_GET_USER_BY_ID = "SELECT * from users where id = :id";
    private final String SQL_ADD_USER = "INSERT INTO users (login,password,firstName,secondName) VALUES (:login,:password,:firstName,:secondName)";
    private final String SQL_GET_ALL_USER = "SELECT * from users";


    private UserMapper userMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private UserNameMapper userNameMapper;


    public UserRepository(UserMapper userMapper, JdbcTemplate jdbcTemplate){
        jdbcTemplate.execute(CREATE_TABLE);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.userMapper = userMapper;
    }

    public void addUser(User user){
        var param  = new MapSqlParameterSource();
        param.addValue("login",user.login());
        param.addValue("password",user.password());
        param.addValue("firstName",user.firstName());
        param.addValue("secondName",user.secondName());
        jdbcTemplate.update(SQL_ADD_USER,param);
    }

    public User getByLogin(String login){
        var param = new MapSqlParameterSource();
        param.addValue("login",login);
        return jdbcTemplate.query(SQL_GET_USER_BY_LOGIN,param,userMapper).stream().findFirst().orElse(null);
    }

    public List<UserName> getAllUser(){
        var param = new MapSqlParameterSource();
        return jdbcTemplate.query(SQL_GET_ALL_USER,param,userNameMapper);
    }

    public User getById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        Optional<User> os = jdbcTemplate.query(SQL_GET_USER_BY_ID,param,userMapper).stream().findFirst();
        return os.orElse(null);
    }
}
