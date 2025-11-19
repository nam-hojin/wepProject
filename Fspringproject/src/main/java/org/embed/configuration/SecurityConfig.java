package org.embed.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스 허용
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // 관리자 페이지 허용 (세션에서 직접 체크)
                .requestMatchers("/admin/**").permitAll()
                // 나머지 요청 모두 허용
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form
                .loginPage("/login.to") // 커스텀 로그인 페이지
                .loginProcessingUrl("/login") // 로그인 form action
                .defaultSuccessUrl("/main.to") // 로그인 성공 시 이동
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/main.to")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인메모리 사용자 정의 (테스트용)
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        var user = User.builder()
                       .username("user")
                       .password(passwordEncoder.encode("user123"))
                       .roles("USER")
                       .build();

        var admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin1234"))
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}