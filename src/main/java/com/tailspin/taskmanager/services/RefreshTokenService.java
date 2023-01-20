package com.tailspin.taskmanager.services;


import com.tailspin.taskmanager.model.RefreshToken;
import com.tailspin.taskmanager.repository.RefreshTokenRepository;
import com.tailspin.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh.validity}")
    private Long refreshTokenDuration;

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken findByToken(String token){
        return tokenRepository.findByToken(token);
    }


    public RefreshToken createRefreshToken(long userId){
        Instant ex = Instant.now().plusMillis(refreshTokenDuration);
        Timestamp expireDate = Timestamp.from(ex);
        String token = (UUID.randomUUID().toString());
        RefreshToken refreshToken = new RefreshToken(-1,userId,token,expireDate);
        tokenRepository.addRefreshToken(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        tokenRepository.deleteByUserId(token.user_id());
        if(token.expiryDate().toInstant().compareTo(Instant.now()) < 0){
            return null;
        }
        return token;
    }


    public int deleteByUserId(int userId){
        return tokenRepository.deleteByUserId(userId);
    }
}
