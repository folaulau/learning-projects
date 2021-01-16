package com.lovemesomecoding.pizzaria.utils;

import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtils {

	
	public static final String FINGER_PRINT_HEADER = "fingerprint";
	
	private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public static String generateBasicAuthenticationToken(String username, String password) {
		String auth = username + ":" + password;
		return "Basic " + new String(Base64.getEncoder().encodeToString(auth.getBytes()));
	}

	public static String getRequestIP(HttpServletRequest request) {
		String requestorIPAddress = "";

		if (request != null) {
			requestorIPAddress = request.getHeader("X-FORWARDED-FOR");
			if (requestorIPAddress == null || "".equals(requestorIPAddress)) {
				requestorIPAddress = request.getRemoteAddr();
			}
		}
		return requestorIPAddress;
	}

	public static String getRequestUserAgent(HttpServletRequest request) {
		String requestorUserAgent = "";
		if (request != null) {
			requestorUserAgent = request.getHeader("user-agent");
			return (requestorUserAgent != null && "".equals(requestorUserAgent) == false) ? requestorUserAgent : "";
		}
		return requestorUserAgent;
	}

	public static Map<String, Object> getRequestHeaderInfo(HttpServletRequest request) {
		Map<String, Object> obj = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);

			obj.put("header_" + headerName, headerValue);
		}
		for (String headerName : IP_HEADER_CANDIDATES) {
			String headerValue = request.getHeader(headerName);
			obj.put("header_x_" + headerName, headerValue);

		}
		return obj;
	}

	public static String getHeaderValue(HttpServletRequest request, String header) {

		if(header==null) {
			return null;
		}
		if(request==null) {
			RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
			if (RequestContextHolder.getRequestAttributes() != null) {
			     request = ((ServletRequestAttributes) attributes).getRequest();
			}
		}
		
		if(request==null) {
			return null;
		}
		
		return request.getHeader(header);
	}
	
	public static String getFullURL(HttpServletRequest request) {
	    StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}

}
