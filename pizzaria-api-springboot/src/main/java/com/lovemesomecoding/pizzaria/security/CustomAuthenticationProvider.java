package com.lovemesomecoding.pizzaria.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lovemesomecoding.pizzaria.entity.user.User;
import com.lovemesomecoding.pizzaria.entity.user.UserDAO;
import com.lovemesomecoding.pizzaria.entity.user.UserStatus;
import com.lovemesomecoding.pizzaria.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CustomAuthenticationProvider
 * 
 * @author fkaveinga
 *
 */
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDAO userDAO;

    /**
     * Authenticate user by credentials
     * 
     * @author fkaveinga
     * @return Authentication
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("authenticate(...)");
        Map<String, String> details = (Map) authentication.getDetails();
        String type = details.get("type");
        log.debug("authentication type: {}", type);

        switch (type) {
            case "password":
                String email = authentication.getPrincipal().toString();
                String password = authentication.getCredentials().toString();
                return loginWithPassword(email, password);
            default:
                throw new BadCredentialsException("Username or password is invalid");
        }
    }

    private Authentication loginAdminWithPassword(String email, String password) {
        User user = null;
        UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(user.getEmail(), password, generateAuthorities(user.generateStrRoles()));
        userAuthToken.setDetails(user);
        return userAuthToken;
    }

    private Authentication loginWithPassword(String email, String password) {
        log.debug("loginWithPassword({})", email);
        User user = userDAO.getByEmail(email);

        if (user == null) {
            log.debug("user not found");
            throw new UsernameNotFoundException("Oops! Your email or password is incorrect.");
        }

        log.debug("member found for {}", email);

        if (user.getPassword() == null || !PasswordUtils.verify(password, user.getPassword())) {
            log.debug("login credentials not matched");
            throw new BadCredentialsException("Oops! Your email or password is incorrect.");
        }

        if (UserStatus.isActive(user.getStatus()) == false) {
            log.debug("Your account has beeen deactivated");
            throw new DisabledException("Your account has beeen deactivated");
        }

        UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), generateAuthorities(user.generateStrRoles()));
        userAuthToken.setDetails(user);

        return userAuthToken;
    }

    // private Authentication loginWithFingerPrint(String fingerPrint) {
    // log.debug("loginWithFingerPrint({})", fingerPrint);
    // Member member = fingerPrintTokenService.getUserByToken(fingerPrint);
    //
    // if (member != null) {
    // fingerPrintTokenService.remove(fingerPrint);
    // return new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword(),
    // generateAuthorities(member.generateStrRoles()));
    // }
    //
    // throw new BadCredentialsException("Invalid finger print token");
    //
    // }

    /**
     * Get Authorities for User
     * 
     * @param user
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> generateAuthorities(Set<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        }

        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
