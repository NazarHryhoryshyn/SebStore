package ua.sombra.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ua.sombra.webstore.service.security.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new UserDetailsServiceImpl());
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder(11));
	}

	 @Override
	    public UserDetailsService userDetailsServiceBean() {
	        return new UserDetailsServiceImpl();
	    }
	
	 
	 @Bean
		public BCryptPasswordEncoder bBCryptPasswordEncoder() {
			return new BCryptPasswordEncoder(11);
		}
	 
	 @Import(AuthenticationConfiguration.class)
		public @interface EnableGlobalAuthentication {}
		
		@Autowired
		public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
		     this.authenticationConfiguration = authenticationConfiguration;
		}
	 
		@Bean
		public AuthenticationManager authManager() throws Exception{
			return authenticationConfiguration.getAuthenticationManager();
		}
		
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/login", "/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN") 
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.permitAll();
	}
}
