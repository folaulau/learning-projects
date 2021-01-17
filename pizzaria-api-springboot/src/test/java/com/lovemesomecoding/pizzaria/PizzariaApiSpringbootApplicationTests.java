package com.lovemesomecoding.pizzaria;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.lovemesomecoding.pizzaria.dto.AuthenticationResponseDTO;
import com.lovemesomecoding.pizzaria.utils.HttpRequestInterceptor;
import com.lovemesomecoding.pizzaria.utils.HttpUtils;
import com.lovemesomecoding.pizzaria.utils.ObjMapperUtils;
import com.lovemesomecoding.pizzaria.utils.PathUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("local")
@SpringBootTest
public class PizzariaApiSpringbootApplicationTests {

    private RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

    @Value("${project.host}")
    private String       host;

    @Value("${server.port}")
    private String       hostPort;

    @BeforeEach
    public void setup() {
        restTemplate.getInterceptors().add(new HttpRequestInterceptor());
    }
//
//    @Test
//    public void test_login_successfully() {
//        String authorization = HttpUtils.generateBasicAuthenticationToken("folaudev@gmail.com", "Test1234!");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authorization);
//        headers.set("x-api-key", "test-key");
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(new String(), headers);
//
//        ResponseEntity<AuthenticationResponseDTO> response = null;
//        AuthenticationResponseDTO authResponse = null;
//
//        String url = "http://" + host + ":" + hostPort + "/api" + PathUtils.LOGIN_URL + "&type=password";
//
//        log.info("url={}", url);
//
//        try {
//
//            response = restTemplate.exchange(new URI(url), HttpMethod.POST, entity, AuthenticationResponseDTO.class);
//
//            authResponse = response.getBody();
//        } catch (Exception e) {
//            log.warn("Exception, msg: {}", e.getMessage());
//            log.warn(ObjMapperUtils.toJson(response));
//        }
//
//        assertThat(authResponse).isNotNull();
//        assertThat(authResponse.getToken()).isNotNull();
//
//    }

}
