package com.hobbydobby.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http : HttpSecurity) {
        http.csrf().ignoringAntMatchers("/gui","/graphql").and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
    }
}