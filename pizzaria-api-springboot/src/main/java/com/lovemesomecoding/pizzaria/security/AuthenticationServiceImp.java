package com.lovemesomecoding.pizzaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovemesomecoding.pizzaria.dto.AuthenticationResponseDTO;
import com.lovemesomecoding.pizzaria.dto.EntityDTOMapper;
import com.lovemesomecoding.pizzaria.entity.user.User;
import com.lovemesomecoding.pizzaria.security.jwt.JwtPayload;
import com.lovemesomecoding.pizzaria.security.jwt.JwtTokenUtils;
import com.lovemesomecoding.pizzaria.utils.ObjMapperUtils;
import com.lovemesomecoding.pizzaria.utils.RandomGeneratorUtils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EntityDTOMapper    entityMapper;

    // authenticate partner
    @Override
    public AuthenticationResponseDTO authenticate(User user) {
        log.debug("authenticate, user={}", ObjMapperUtils.toJson(user));
        String jwtToken = JwtTokenUtils.generateToken(new JwtPayload(RandomGeneratorUtils.getJWTId(), user.getUuid(), getRequestUserAgent(), user.generateStrRoles()));

        AuthenticationResponseDTO authenticatedSessionDTO = entityMapper.mapUserToUserAuthSuccessDTO(user);
        authenticatedSessionDTO.setToken(jwtToken);

        return authenticatedSessionDTO;
    }

    private String getRequestUserAgent() {

        String userAgent = null;
        boolean mobile = false;

        try {
            userAgent = request.getHeader("mobile-agent");

        } catch (Exception e) {
            log.error("Exception while getting header from request -", e);
        }

        if (userAgent != null && !userAgent.isEmpty()) {
            mobile = true;
        } else {

            userAgent = request.getHeader("user-agent");

        }

        return userAgent;

    }

}
