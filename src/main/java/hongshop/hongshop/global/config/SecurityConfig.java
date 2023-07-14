package hongshop.hongshop.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @fileName SecurityConfig
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-06-17
 * @summary
 *               참고 ; https://chlee21.tistory.com/190
 **/

@Configuration
public class SecurityConfig  {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().headers().frameOptions().disable();       // csrf 및 frame 옵션 비활서오하 -> 애플리케이션을 iframe에 임베드 가능하도록 한다.
        http
                .authorizeRequests(authorize ->                         // url 기반 보안을 구성하는 http 객체의 메서드 -> 다양한 유형의 사용자가 액세스할 수 있는 url 정의
                        authorize
                                .antMatchers("/login").permitAll()                                  // 로그인 페이지의 경우 모두 접근 가능
                                .antMatchers("/user/**").access("hasRole('ROLE_USER')")     // "/user/"로 시작하는 모든 url은 "ROLE_USER"의 역할을 갖은 사용자만 액세스 가능
                                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")   // "/admin/"로 시작하는 모든 url은 "ROLE_ADMIN"의 역할을 갖은 사용자만 액세스 가능
                                .anyRequest().permitAll()                                             // 앞선 패턴들을 제외한 다른 요청들은 모두에게 허용
                        )
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                .loginPage("/login")                    // 로그인 페이지가 있는 url 지정
                                .usernameParameter("userId")            // 사용자 이름 필드에 사용되는 매개변수 이름의 기본값인 "username" 대신 "userId" 사용하도록 설정
                                .loginProcessingUrl("/loginProc")       // 처리를 위해 로그인 양식을 제출할 url을 지정
                                .defaultSuccessUrl("/")                 // 로그인 성공 후, 리다렉션할 기본 url 설정

                );

        return http.build();                                            // build된 http 객체를 반환 -> spring 보안 framework에 적용된다.
    }
}
