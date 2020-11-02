package ma.project.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
/*import org.springframework.context.annotation.Bean;*/
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	 //pour utiliser la meme base de donnes
	private DataSource datasource;
	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		

		auth.jdbcAuthentication().dataSource(datasource)
		.usersByUsernameQuery("select username as principal,password as credentials,active from users where username =?")
		.authoritiesByUsernameQuery("select username as principal,role as role from usersroles where username=?")
		.passwordEncoder(passwordEncoder())
		.rolePrefix("ROLE_");

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Passer par une authentifaction base sur le formulaire
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/consultercompte","/comptes").hasRole("USER");
		http.authorizeRequests().antMatchers("/saveOperation","/clients","/clients/*","/listofcompte").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	


}
