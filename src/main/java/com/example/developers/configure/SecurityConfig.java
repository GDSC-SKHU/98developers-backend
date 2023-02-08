package com.example.developers.configure;

import com.example.developers.jwt.JwtFilter;
import com.example.developers.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Bean을 사용해 수동으로 빈을 등록해줄 때에는 메소드 이름으로 빈 이름이 결정된다.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()// Request에 인증, 인가(권한 있냐?)를 부여하겠다.
                .antMatchers("/join","/index", "/login").permitAll()  // /index /login 은 인가가 필요없다.
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")// /user  uri는 USER 롤 또는 ADMIN 롤이 있어야 접속가능
                .antMatchers("/admin").hasRole("ADMIN") // /admin uri는 ADMIN 롤이 있어야 접속가능.
                .anyRequest().authenticated() // 그 외에는 인증된 모든 사용자가 URL을 허용하도록 지정합니다.
                .and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}