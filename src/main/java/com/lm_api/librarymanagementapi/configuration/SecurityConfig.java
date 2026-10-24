package com.lm_api.librarymanagementapi.configuration;


import com.lm_api.librarymanagementapi.dto.UserRole;
import com.lm_api.librarymanagementapi.security.AuthEntryPoint;
import com.lm_api.librarymanagementapi.security.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private AuthEntryPoint authEntryPoint;
    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a

                        // Public endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/auth/**",
                                "/api/v1/refreshToken"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/v1/users"
                        ).permitAll()
                        // Members can view books and categories,and their own loans
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/books",
                                "/api/v1/books",
                                "/api/v1/books/**",
                                "/api/v1/categories",
                                "/api/v1/categories/**",
                                "/api/v1/loans",
                                "/api/v1/loans/**",
                                "/api/v1/users/**")
                        .hasAnyRole("ADMIN", "MEMBER")

                        .requestMatchers(HttpMethod.PUT,"/api/v1/users/**")
                        .hasAnyRole("ADMIN","MEMBER")

                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**")
                        .hasAnyRole("ADMIN","MEMBER")

                        .requestMatchers("/api/v1/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                );

        httpSecurity.addFilterBefore(
                authTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return httpSecurity.build();
    }
}
