package com.cos.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
// secured 어노테이션 활성화. 메서드 레벨에서의 접근 권한 세팅 가능.
// preAuthorize 어노테이션 활성화.
// secured는 한 개의 권한체크, preAuthorize는 두 개 이상의 권한 체크.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    // 해당 메서드의 리턴 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/loginForm")      // 로그인 페이지의 URL을 설정. 커스텀 로그인 페이지 지정 가능.
                        .loginProcessingUrl("/login") // 로그인 처리를 담당하는 URL을 설정. login 주소가 호출되면 시큐리티가 이를 낚아챔.
                                                      // 로그인 폼의 action 속성은 해당 URL과 동일해야 함. 이 경로로 POST요청이 오면 스프링 시큐리티가 인증 수행
                                                      // 실제 페이지 렌더링 X. 인증 성공 시 설정된 리다이렉트 URL로 이동.
                        .defaultSuccessUrl("/")       // 로그인 성공 후 URL
                );

        return http.build();
    }
}
