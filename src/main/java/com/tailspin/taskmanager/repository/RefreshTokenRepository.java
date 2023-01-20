package com.tailspin.taskmanager.repository;

import com.tailspin.taskmanager.model.RefreshToken;
import com.tailspin.taskmanager.model.mapper.RefreshTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class RefreshTokenRepository {

    private final String CREATE_TABLE = "create table if not exists tokens(" +
            "id SERIAL NOT NULL," +
            "user_id integer NOT NULL," +
            "token text NOT NULL," +
            "expiryDate timestamp NOT NULL)";


    private final String SQL_GET_BY_TOKEN = "SELECT * from tokens where token = :token";
    private final String SQL_DELETE_BY_USER = "DELETE from tokens where user_id = :user_id";
    private final String SQL_ADD_REFRESH_TOKEN = "INSERT into tokens (user_id,token,expiryDate) values (:user_id,:token,:expiryDate)";
    @Autowired
    private RefreshTokenMapper mapper;


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RefreshTokenRepository(JdbcTemplate jdbcTemplate){
        jdbcTemplate.execute(CREATE_TABLE);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public RefreshToken findByToken(String token){
        var param = new MapSqlParameterSource();
        param.addValue("token",token);
        return jdbcTemplate.query(SQL_GET_BY_TOKEN,param,mapper).stream().findFirst().orElse(null);
    }


    public int deleteByUserId(long userId){
        var param = new MapSqlParameterSource();
        param.addValue("user_id",userId);
        return jdbcTemplate.update(SQL_DELETE_BY_USER,param);
    }


    public void addRefreshToken(RefreshToken token){
        var param = new MapSqlParameterSource();
        param.addValue("user_id",token.user_id());
        param.addValue("token",token.token());
        param.addValue("expiryDate",token.expiryDate());
        jdbcTemplate.update(SQL_ADD_REFRESH_TOKEN,param);
    }
}
