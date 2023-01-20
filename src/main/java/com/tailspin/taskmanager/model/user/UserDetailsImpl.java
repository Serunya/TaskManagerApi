package com.tailspin.taskmanager.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long id, String login,String password, String firstName, String secondName)
    {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    
    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(user.id(),user.login(), user.password(), user.firstName(), user.secondName());
    }
}
