package name.lenmar.publications.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "swagger-ui",
                        "swagger-ui/**",
                        "swagger-resources/**",
                        "v3/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/article/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/article").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/article/**").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/article/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/article/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/stat").hasRole("ADMIN")
                //.anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
