package com.lovemesomecoding.pizzaria.cache;

import java.util.Map;
import java.util.Optional;

import com.lovemesomecoding.pizzaria.dto.ApiSessionDTO;

public interface CacheService {

    public void addUpdate(String token, ApiSessionDTO apiSession);

    /**
     * might not be there. called at authentication
     * 
     * @param token
     * @return ApiSession
     */
    public Optional<ApiSessionDTO> findApiSessionToken(String token);

    /**
     * must be there. called after token has been authenticated
     * 
     * @param token
     * @return ApiSession
     */
    public ApiSessionDTO getApiSessionToken(String token);

    public long delete(String token);

    public Map<Object, Object> findAllApiSessions();
    
}
