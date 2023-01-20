package com.tailspin.taskmanager.model;

import java.util.Date;

public record Message(
        long id,
        long senderId,
        long chatId,
        Date timestamp,
        String content) {
}
