package com.example.hackathon.security;

import com.example.hackathon.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomUserDetail implements UserDetails, OAuth2User {
    private UserEntity userEntity;

    public Long getId() {
        return userEntity.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
}
