package world.evgereo.household.householdapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import world.evgereo.household.householdapi.security.jwt.JwtFilter;
import world.evgereo.household.householdapi.security.manager.HouseAuthorizationManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class FilterChainConfig {
    private final HouseAuthorizationManager houseAuthorizationManager;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/user/create", "/sign-in").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/house/{houseId}/resident**").access(houseAuthorizationManager)
                    .requestMatchers(HttpMethod.DELETE, "/house/{houseId}/resident**").access(houseAuthorizationManager)
                    .requestMatchers("/user/**", "/house/**").authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception
                    .accessDeniedHandler((request, response, accessDeniedException)
                            -> response.setStatus(HttpStatus.FORBIDDEN.value()))
                    .authenticationEntryPoint((request, response, authenticationException)
                            -> response.getWriter().write("")))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
