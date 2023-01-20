package com.tailspin.taskmanager.repository;


import com.tailspin.taskmanager.model.Point;
import com.tailspin.taskmanager.model.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointsRepository {
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS points(" +
            "id SERIAL NOT NULL," +
            "content text NOT NULL," +
            "isDone boolean NOT NULL," +
            "taskId integer NOT NULL)";


    private final String SQL_ADD_POINT = "INSERT INTO points (content,isDone,taskId) VALUES (:content,:isDone,:taskId)";
    private final String SQL_GET_BY_TASKID = "SELECT * FROM points Where taskid = :taskId";
    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    private PointMapper mapper;

    public PointsRepository(JdbcTemplate jdbcTemplate){
        jdbcTemplate.execute(CREATE_TABLE);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public void addPoint(Point point){
        var param = new MapSqlParameterSource();
        param.addValue("content",point.content());
        param.addValue("isDone",point.isDone());
        param.addValue("taskId",point.taskId());
        jdbcTemplate.update(SQL_ADD_POINT,param);
    }


    public List<Point> getPointByTaskId(int taskId){
        var param = new MapSqlParameterSource();
        param.addValue("taskId",taskId);
        return jdbcTemplate.query(SQL_GET_BY_TASKID,param, mapper);
    }


}
