package cl.huerta.codingdojo.subscriptions.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//imports removed for brevity
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
    
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	     http.
	         authorizeRequests()
	             .antMatchers("/css/**", "/js/**", "/registration").permitAll()
	             .antMatchers("/packages").access("hasRole('ADMIN')")
	             .anyRequest().authenticated()
	             .and()
	         .csrf()
	         	.ignoringAntMatchers("/logout")
	         	.and()
	         .formLogin()
	             .loginPage("/")
	             .loginProcessingUrl("/login")
	             .defaultSuccessUrl("/", true)
	             .permitAll()
	             .and()
	         .logout()
	             .permitAll()
	         ;
	 }
	 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}