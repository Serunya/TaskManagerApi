package com.tailspin.taskmanager.services;

import com.tailspin.taskmanager.model.user.User;
import com.tailspin.taskmanager.model.user.UserName;
import com.tailspin.taskmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService{
    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public void addUser(String login, String password,String firstName,String secondName){
        repository.addUser(new User(-1,login,password,firstName,secondName));
    }

    public User getByLogin(String login){
        return repository.getByLogin(login);
    }

    public boolean isUserExist(String login){
        User user = repository.getByLogin(login);
        return user != null;
    }

    public User getById(int userId){
        return repository.getById(userId);
    }

    public List<UserName> getAllUser(){
        return repository.getAllUser();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByLogin(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("User %s is not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.login(), user.password(), true, true, true, true, new HashSet<>());
    }
}
