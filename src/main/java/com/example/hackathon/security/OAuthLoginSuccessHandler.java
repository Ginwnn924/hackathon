package com.example.hackathon.security;

import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${GOOGLE_REDIRECT_FE}")
    private String googleRedirectUrl;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        CustomUserDetail userDetail;

        if (principal instanceof CustomUserDetail) {
            userDetail = (CustomUserDetail) principal;
        } else if (principal instanceof DefaultOidcUser) {
            // Nếu là OAuth2 login
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;


            // Lấy thông tin từ DefaultOidcUser
            String email = oidcUser.getAttribute("email");
            String name = oidcUser.getAttribute("name");
            String img = oidcUser.getAttribute("picture");
            // Kiểm tra user đã có trong DB chưa
            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
            UserEntity user;
            if(userOpt.isPresent()) {
                user = userOpt.get();
            } else {
                // Nếu lần đầu login, tạo mới user
                user = new UserEntity();
                user.setEmail(email);
                user.setName(name);
                user.setCreated_at(LocalDateTime.now());
                user.setUpdated_at(LocalDateTime.now());
                user.setImg(img);
                userRepository.save(user);
            }
            userDetail = new CustomUserDetail(user);
        } else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
        }

        String token = jwtService.generateAccessToken(userDetail);

        String targetUrl = googleRedirectUrl + "?token=" + token;

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }




}