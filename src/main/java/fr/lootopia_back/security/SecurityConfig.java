package fr.lootopia_back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private CustomAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // Allow all requests for authentication and registration endpoints
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**")
            .permitAll()
            // Allow all requests for public endpoints
            .requestMatchers("/api/me").authenticated()
            .requestMatchers("/api/treasure-hunts/all").authenticated()
            .requestMatchers("/api/treasure-hunts/**").authenticated()
            .requestMatchers("/api/treasure-hunt-participants/**").permitAll()
            .requestMatchers("/api/steps/**").authenticated()
            .requestMatchers("/api/steps-validation/**").authenticated()

            // Allow all requests for roles ADMIN and ORGANIZER
            .requestMatchers("/api/treasure-hunts").hasAnyRole("ORGANIZER", "ADMIN")

            // Admin endpoints
            .requestMatchers("/api/users").hasRole("ADMIN")
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .exceptionHandling(exceptions -> exceptions
            .accessDeniedHandler(accessDeniedHandler))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
