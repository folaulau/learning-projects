package com.lovemesomecoding.pizzaria.security;

import com.lovemesomecoding.pizzaria.dto.AuthenticationResponseDTO;
import com.lovemesomecoding.pizzaria.entity.user.User;

public interface AuthenticationService {

    AuthenticationResponseDTO authenticate(User user);

}
