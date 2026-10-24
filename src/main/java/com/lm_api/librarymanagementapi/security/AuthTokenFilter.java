package com.lm_api.librarymanagementapi.security;


import com.lm_api.librarymanagementapi.service.CustomUserDetailService;
import com.lm_api.librarymanagementapi.service.TokenBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter{


    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final TokenBlackListService tokenBlackListService;
    @Autowired
    public AuthTokenFilter(JwtUtil jwtUtil,
                           CustomUserDetailService customUserDetailService,
                           TokenBlackListService tokenBlackListService){
        this.customUserDetailService=customUserDetailService;
        this.jwtUtil=jwtUtil;
        this.tokenBlackListService=tokenBlackListService;
    }

    private String parseJwt(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if (header!=null && header.startsWith("Bearer ")){
            return  header.substring(7);
        }
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws IOException,ServletException {
        try {
            String jwt=parseJwt(request);
            if(jwt!=null && jwtUtil.validateJwtToken(jwt) && !tokenBlackListService.isAccessRevoked(jwt)){
                String username=jwtUtil.getUsernameFromToken(jwt);
                UserDetails userDetails=customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);

    }}catch (Exception e){
            log.error("cannot set user authentication");}
        filterChain.doFilter(request,response);

}}
