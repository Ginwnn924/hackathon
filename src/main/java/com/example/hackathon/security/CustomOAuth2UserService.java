package com.example.hackathon.security;

import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.repository.UserRepository;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2User);
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(   ex.getMessage(), ex.getCause());
        }
    }




    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Optional<UserEntity> userOptional = userRepository.findByEmail(attributes.get("email").toString());
        UserEntity user = new UserEntity();
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if (!attributes.get("name").equals(user.getName()) || !attributes.get("picture").equals(user.getImg())) {
                user.setName(attributes.get("name").toString());
                user.setImg(attributes.get("picture").toString());
                user.setUpdated_at(LocalDateTime.now());
                user = userRepository.save(user);
            }
        } else {
            user.setEmail(attributes.get("email").toString());
            user.setName(attributes.get("name").toString());
            user.setImg(attributes.get("picture").toString());
            user.setCreated_at(LocalDateTime.now());
            user.setUpdated_at(LocalDateTime.now());
            user = userRepository.save(user);
        }

        return new CustomUserDetail(user);
    }

}
