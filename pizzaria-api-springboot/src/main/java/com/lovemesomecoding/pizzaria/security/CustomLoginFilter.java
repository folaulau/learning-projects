package com.lovemesomecoding.pizzaria.security;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.lovemesomecoding.pizzaria.dto.AuthenticationResponseDTO;
import com.lovemesomecoding.pizzaria.entity.user.User;
import com.lovemesomecoding.pizzaria.exception.ApiErrorResponse;
import com.lovemesomecoding.pizzaria.exception.ApiException;
import com.lovemesomecoding.pizzaria.utils.HttpUtils;
import com.lovemesomecoding.pizzaria.utils.ObjMapperUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CustomUsernamePassworAuthenticationFilter
 * 
 * @author fkaveinga
 *
 */
@Slf4j
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthenticationService authenticationService;

    private Map<String, String>   authenticationDetails = new HashMap<>();

    public CustomLoginFilter(String loginUrl, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(loginUrl));
        setAuthenticationManager(authManager);
    }

    /**
     * Attemp Authentication process. Pass username and password to authentication provider
     * 
     * @author fkaveinga
     * @return Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("attemptAuthentication ... Method={}", request.getMethod());

        String authorizationHeader = request.getHeader("Authorization");
        log.debug("Login Authorization Header: {}", authorizationHeader);
        if (authorizationHeader == null) {
            throw new InsufficientAuthenticationException("Authorization Header is null");
        }

        String loginType = request.getParameter("type");
        log.debug("loginType: {}", loginType);
        if (loginType == null) {
            throw new InsufficientAuthenticationException("login type is missing");
        }

        switch (loginType) {
            case "password":
                String email = getUsername(authorizationHeader);
                String password = getPassword(authorizationHeader);
                log.debug("email: {}", email);
                log.debug("password: {}", password);
                if (email == null || email.isEmpty()) {
                    log.debug("username is null");
                    throw new InsufficientAuthenticationException("Username is null");
                }

                if (password == null || password.isEmpty()) {
                    log.debug("password is null");
                    throw new InsufficientAuthenticationException("Password is null");
                }
                return authenticateWithPassword(email, password, loginType);
            case "finger-print":
                String accessToken = getAccessToken(authorizationHeader);
                if (accessToken == null || accessToken.isEmpty()) {
                    log.debug("finger print token is null");
                    throw new InsufficientAuthenticationException("finger print token is null");
                }
                return authenticateWithFingerPrint(accessToken);

            default:
                log.debug("wrong login type {}", loginType);
                throw new InsufficientAuthenticationException("Wrong login type");
        }

    }

    private Authentication authenticateWithPassword(String email, String password, String type) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationDetails.put("type", type);
        usernamePasswordAuthenticationToken.setDetails(authenticationDetails);
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    private Authentication authenticateWithFingerPrint(String fingerPrint) {
        log.debug("authenticateWithFingerPrint(..)");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(fingerPrint, fingerPrint);
        authenticationDetails.put("type", "finger-print");
        usernamePasswordAuthenticationToken.setDetails(authenticationDetails);
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    /**
     * Write response when request was successful.
     * 
     * @author fkaveinga
     * @return HttpServletResponse
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("successfulAuthentication(...), METHOD={}", request.getMethod());
        String clientIpAddress = HttpUtils.getRequestIP(request);
        String clientUserAgent = HttpUtils.getRequestUserAgent(request);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        User user = (User) authResult.getDetails();

        // log.info(ObjectUtils.toJson(user));

        log.debug("\n\nlogin successful for {} \n\n", user.getEmail());

        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(user);

        ObjMapperUtils.getObjectMapper().writeValue(response.getWriter(), authenticationResponseDTO);
    }

    /**
     * Write response when request was unsuccessful.
     * 
     * @author fkaveinga
     * @return HttpServletResponse
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.debug("unsuccessfulAuthentication(...)");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(BAD_REQUEST.value());

        String message = failed.getLocalizedMessage();
        log.debug("Error message: {}", message);

        ObjMapperUtils.getObjectMapper()
                .writeValue(response.getWriter(),
                        new ApiErrorResponse(BAD_REQUEST, StringUtils.defaultString(message, ApiErrorResponse.DEFAULT_MSG), Arrays.asList(message, "I suggest you take a walk to clear your mind")));
    }

    /**
     * Parse token for username
     * 
     * @param authorizationHeader
     * @return String username
     */
    private String getUsername(String authorizationHeader) {
        log.debug("getUsername(..)");
        String username = null;
        try {
            String usernamePasswordToken = StringUtils.substringAfter(authorizationHeader, " ").trim();
            // log.debug("usernamePasswordToken: {}",usernamePasswordToken);
            String rawToken = this.decodeBase64Token(usernamePasswordToken);
            log.debug("rawToken: {}", rawToken);
            username = StringUtils.substringBefore(rawToken, ":");
            log.debug("username: {}", username);
        } catch (Exception e) {
            throw new ApiException("Invalid username");
        }
        return username;
    }

    /**
     * Parse token for password
     * 
     * @param authorizationHeader
     * @return String password
     */
    private String getPassword(String authorizationHeader) {
        log.debug("getPassword(..)");
        String password = null;
        try {
            String usernamePasswordToken = StringUtils.substringAfter(authorizationHeader, " ").trim();

            String rawToken = this.decodeBase64Token(usernamePasswordToken);
            log.debug("rawToken: {}", rawToken);
            password = StringUtils.substringAfter(rawToken, ":");
            log.debug("username: {}", password);
            return password;
        } catch (Exception e) {
            throw new ApiException("Invalid password");
        }

    }

    /**
     * Parse for access token
     * 
     * @param authorizationHeader
     * @return String access token
     */
    private String getAccessToken(String authorizationHeader) {
        log.debug("getAccessToken(..)");
        String bearerToken = null;
        try {
            bearerToken = StringUtils.substringAfter(authorizationHeader, " ").trim();
            log.debug("bearerToken: {}", bearerToken);
        } catch (Exception e) {
            throw new ApiException("Invalid token");
        }
        return bearerToken;
    }

    /**
     * Decode authentication token
     * 
     * @param usernamePasswordToken
     * @return String
     */
    private String decodeBase64Token(String usernamePasswordToken) {
        byte[] decodedBytes = Base64.getDecoder().decode(usernamePasswordToken);
        return new String(decodedBytes);
    }

}
