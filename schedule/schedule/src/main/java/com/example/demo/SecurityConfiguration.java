package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {  

    @Bean
    public UserDetailsService userDetailsService() {
        return new TraineeService(); //this service is used to withold the users details (id, name etc)
    }

    public BCryptPasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder(); //uses BCryptPasswordEncoder (10 rounds)
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());

        authProvider.setPasswordEncoder(passEncoder());

        return authProvider;
    }

    @Override  
    public void configure(HttpSecurity http) throws Exception { 
        http    
        .authorizeRequests()  
            .antMatchers( "/css/**").permitAll() // permits unauthorized access to the css directory 
            .anyRequest().authenticated()  
                .and()  
            .formLogin()  
                .loginPage("/login")  //the page for logging in is my /login route
                .failureUrl("/login-error")  //if it fails, it goes here
                .permitAll() //once signed in, everything is permitted
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
            // .and()
            // .rememberMe().tokenRepository(persistentTokenRepository())
            // .and();


    }  

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
        auth.authenticationProvider(authenticationProvider());
    }  

    // @Bean
    // public PersistentTokenRepository persistentTokenRepository(){
    //     JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    //     tokenRepository.setDataSource(datasource());
	//     return tokenRepository;
        
    // }
    

}
