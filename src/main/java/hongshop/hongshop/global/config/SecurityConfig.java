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

        http.csrf().disable().headers().frameOptions().disable();
        http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers("/user/**").authenticated()
                                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                                .anyRequest().permitAll()
                        )
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                .usernameParameter("userId")
                                .loginPage("/login")
                                .loginProcessingUrl("/loginProc")
                                .defaultSuccessUrl("/")

                );

        return http.build();
    }
}
