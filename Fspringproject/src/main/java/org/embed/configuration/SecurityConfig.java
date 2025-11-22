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
		http.authorizeHttpRequests(auth -> auth

				.requestMatchers("/static/css/**", "/static/js/**", "/static/images/**").permitAll()

				.requestMatchers("/admin/**").permitAll()

				.anyRequest().permitAll()).csrf(csrf -> csrf.disable())
				.formLogin(form -> form.loginPage("/login.to").loginProcessingUrl("/login")
						.defaultSuccessUrl("/main.to").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/main.to").permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
		var user = User.builder().username("user").password(passwordEncoder.encode("user123")).roles("USER").build();

		var admin = User.builder().username("admin").password(passwordEncoder.encode("admin1234")).roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}
}