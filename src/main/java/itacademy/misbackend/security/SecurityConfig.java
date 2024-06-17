package itacademy.misbackend.security;

import itacademy.misbackend.filter.CustomAuthorizationFilter;
import itacademy.misbackend.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepo userRepo;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().
                csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests().requestMatchers("/api/login/**", "/api/token/refresh/**",
                "/api", "/api/logout/**").permitAll();
        http.authorizeHttpRequests().requestMatchers(
                        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html",
                        "/swagger-resources/**", "/webjars/**").permitAll();

        http.authorizeHttpRequests().requestMatchers(HttpMethod.GET,
                "/api/departments/**", "/api/doctors/**", "api/services/**").permitAll();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/patients/**",
                        "/api/medCards/**", "/api/appointments/**", "/api/medicalRecords/**").hasAuthority("DOCTOR")
                .requestMatchers(HttpMethod.POST, "/api/medicalRecords/**").hasAuthority("DOCTOR")
                .requestMatchers(HttpMethod.PUT, "/api/medicalRecords/**").hasAuthority("DOCTOR");

        //закомментила, т.к. пока нет юзера с ролью ADMIN
        http.authorizeHttpRequests().requestMatchers("/api/patients/**", "/api/doctors/**", "/api/departments/**",
                        "/api/services/**", "/api/appointments/**", "/api/medicalRecords/**").hasAuthority("ADMIN");


        http.authorizeHttpRequests().anyRequest().permitAll();//Временно
        http.apply(CustomSecurityDetails.customDsl(userRepo));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.logout().logoutUrl("/api/logout").logoutSuccessUrl("/api/login").invalidateHttpSession(true);
        return http.build();
    }

    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173/", "https://it-academy-mis-app-eb8b8e2f87d7.herokuapp.com/"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
