package com.lovemesomecoding.pizzaria.security;

import com.lovemesomecoding.pizzaria.dto.AuthenticationResponseDTO;
import com.lovemesomecoding.pizzaria.entity.user.User;
import com.lovemesomecoding.pizzaria.security.jwt.JwtPayload;

public interface AuthenticationService {

    AuthenticationResponseDTO authenticate(User user);

    boolean logOutUser(String token);

    boolean authorizeRequest(String token, JwtPayload jwtPayload);

}
