package com.awbd.proiect1.configuration;

import com.awbd.proiect1.domain.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityJDBCConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled "
                        + "from users "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select username, user_type "
                        + "from users "
                        + "where username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/list").hasAuthority(UserType.ADMIN.name())
                .antMatchers("/products/list").permitAll()
                .antMatchers("/products/details/**").permitAll()
                .antMatchers("/products/edit/**").hasAuthority(UserType.ADMIN.name())
                .antMatchers("/products/delete/**").hasAuthority(UserType.ADMIN.name())
                .antMatchers("/products/new/**").hasAuthority(UserType.ADMIN.name())
                .antMatchers("/cart/**").hasAuthority(UserType.CUSTOMER.name())
                .antMatchers("/orders/**").hasAnyAuthority(UserType.CUSTOMER.name(), UserType.ADMIN.name())
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
}
