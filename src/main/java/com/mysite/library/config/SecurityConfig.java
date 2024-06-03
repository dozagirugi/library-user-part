package com.mysite.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers)-> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                //authorizeHttpRequests에는 모두에게 허가되는 요청주소를 등록하자
                .authorizeHttpRequests((authz) ->
                    authz
                            .requestMatchers("/**").permitAll()
                            .anyRequest().authenticated()
                )

                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/user/login")

                                //회원가입 등록 실패시 주소 설정
                                .failureUrl("/fail404?error=true")

                                //성공시 주소 설정
                                .defaultSuccessUrl("/main")

                                //로그인 시 사용할 닉네임을 email로 설정
                                .usernameParameter("email")

                                //비밀번호는 password로 설정하자
                                .passwordParameter("password")
                )

                .logout((logout) ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                                .logoutSuccessUrl("/logout")
                                .invalidateHttpSession(true)
                );

        return http.build();
    }

    //정적 파일을 예외로 처리하기 (시큐리티가 관여하지 않는 부분.)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring().requestMatchers("/css/**", "/js/**", "/error");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
