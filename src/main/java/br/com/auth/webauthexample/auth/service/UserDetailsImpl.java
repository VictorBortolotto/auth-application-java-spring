package br.com.auth.webauthexample.auth.service;

import java.util.Collection;

import br.com.auth.webauthexample.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails{
    
    private static final long serialVersionUID = 1L;

    private long id;
    private String email;

    @JsonIgnore
    private String password;

    public UserDetailsImpl(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static UserDetailsImpl build(UserModel user) {
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
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

}
