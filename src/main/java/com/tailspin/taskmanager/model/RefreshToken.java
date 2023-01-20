package com.tailspin.taskmanager.model;

import java.sql.Timestamp;

public record RefreshToken(
        long id,
        long user_id,
        String token,
        Timestamp expiryDate
) {
}
