package com.app.implementation.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    //method for take a users details from database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        //define query to retrieve a user from username
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");

        //define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");


        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((configurer -> configurer
                .requestMatchers(HttpMethod.GET, "/api/Emp/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/Emp").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/api/Emp").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/api/Emp/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/photo/upload").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET,"/api/photo/id/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/photo").permitAll()
                .requestMatchers(HttpMethod.GET,"api/photo/all").permitAll()
        ));


        //use basic http security
        httpSecurity.httpBasic(Customizer.withDefaults());


        //disable Cross Site Request Forgery (CSRF)
        //in general, not required for stateless REST APIs that POSR,GET,DELETE,PUT
        httpSecurity.csrf(csrf -> csrf.disable());


        return httpSecurity.build();
    }


}

//    /*
//    //create user for who can use or request or response from system(REST APIs)
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails salim = User.builder()
//                .username("salim")
//                .password("{noop}salim@1234")
//                .roles("ADMIN","EMPLOYEE","MANAGER")
//                .build();
//
//        UserDetails Osama=User.builder()
//                .username("Osama")
//                .password("{noop}osama@123")
//                .roles("ADMIN","EMPLOYEE")
//                .build();
//
//        return new InMemoryUserDetailsManager(salim);
//    }
//
//*/
//
//
//
//
//
//}
