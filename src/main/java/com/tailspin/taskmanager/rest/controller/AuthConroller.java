package com.tailspin.taskmanager.rest.controller;


import com.tailspin.taskmanager.model.RefreshToken;
import com.tailspin.taskmanager.model.user.User;
import com.tailspin.taskmanager.model.user.UserDetailsImpl;
import com.tailspin.taskmanager.rest.payload.requests.RefreshRequest;
import com.tailspin.taskmanager.rest.payload.requests.SignInRequest;
import com.tailspin.taskmanager.rest.payload.requests.SignUpRequest;
import com.tailspin.taskmanager.rest.payload.response.SignInResponse;
import com.tailspin.taskmanager.security.jwt.JwtUtils;
import com.tailspin.taskmanager.services.RefreshTokenService;
import com.tailspin.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/auth/")
public class AuthConroller {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenService tokenService;


    @PostMapping(path = "/signin")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signIn) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getLogin(),signIn.getPass()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accessToken = jwtUtils.generateJwtToken(authentication);
        long user_id = userService.getByLogin(userDetails.getUsername()).id();
        RefreshToken refreshToken = tokenService.createRefreshToken(user_id);
        SignInResponse resp = new SignInResponse(accessToken, refreshToken.token());
        return ResponseEntity.ok().body(resp);
    }


    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUp)
    {
       if(userService.isUserExist(signUp.getLogin())){
           return  ResponseEntity.badRequest().body("Error: login is already in use");
       }
        System.out.println(signUp.getLogin() + " " + signUp.getFirstName() + " " + signUp.getSecondName());
       userService.addUser(signUp.getLogin(),encoder.encode(signUp.getPass()),signUp.getFirstName(),signUp.getSecondName());
       return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping(path = "/refreshtoken")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshRequest request){
        RefreshToken token = tokenService.findByToken(request.refreshToken);
        if(token == null || tokenService.verifyExpiration(token) == null){
            return ResponseEntity.badRequest().build();
        }
        else{
            User user = userService.getById((int) token.user_id());
            String access_token = jwtUtils.generateTokenFromUsername(user.login());
            String refresh_token = tokenService.createRefreshToken(token.user_id()).token();
            SignInResponse resp = new SignInResponse(access_token,refresh_token);
            return ResponseEntity.ok().body(resp);
        }
    }
}
