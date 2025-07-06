package com.taskamanger.task_manager_app.config;

import com.taskamanger.task_manager_app.service.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

@EnableMethodSecurity // Metot bazlı güvenliği etkinleştirir (örn: @PreAuthorize)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .csrf(AbstractHttpConfigurer::disable) // CSRF korumasını kapat (JWT stateless olduğu için)
//             .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/api/v1/auth/**").permitAll() // Kimlik doğrulama endpoint'lerine herkes erişebilir
//                    .requestMatchers("/tasks/all").hasAnyRole("ADMIN", "USER") // Tüm görevleri görmek için ADMIN veya USER rolü gerekli
//                     .requestMatchers("/tasks/**").hasRole("ADMIN") // Diğer /tasks işlemleri için sadece ADMIN
//                    .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulaması gerektirir
//             )
              .sessionManagement(session -> session
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Oturum yönetimi stateless (JWT için önemli)
              )
              .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresini ekle

        return http.build();
     }
}