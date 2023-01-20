package com.tailspin.taskmanager.model;

/*
 "id SERIAL NOT NULL," +
            "title text NOT NULL," +
            "description text NOT NULL," +
            "executorId integer NOT NULL," +
            "curratorId integer NOT NULL," +
            "deadLine date NOT NULL," +
            "startingDate date NOT NULL)";
 */


import java.sql.Date;

public record Task(
        int id,
        String title,
        String description,
        int executorId,
        int curratorId,
        Date deadLine,
        Date startingDate
) {
}
