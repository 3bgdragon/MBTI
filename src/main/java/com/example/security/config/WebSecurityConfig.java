package com.example.security.config;

import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                   .antMatchers("/login","/signup","/user").permitAll()
                   .antMatchers("/").hasRole("USER")
                   .antMatchers("/admin").hasRole("ADMIN")
                   .anyRequest().authenticated() //나머지요청들은 권하의 종류에 상관없이 권한이 있어야 접근

                .and()
                   .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                .and()
                   .logout()
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)

        ;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
        // loadUserByUsername() 구현해야함 (서비스 참고)
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
