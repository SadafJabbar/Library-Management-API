package com.lm_api.librarymanagementapi.service;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBlackListService {

    private Set<String> revokedAccessToken=ConcurrentHashMap.newKeySet();
    private Set<String> revokedRefreshToken=ConcurrentHashMap.newKeySet();

    public void revokeAccessToken(String accessToken){revokedAccessToken.add(accessToken);}
    public void revokeRefreshToken(String token){revokedRefreshToken.add(token);}
    public Boolean isAccessRevoked(String accessToken){return revokedAccessToken.contains(accessToken);}
    public Boolean isRefreshRevoked(String refreshToken){return revokedRefreshToken.contains(refreshToken);}


}
