package com.blog.bloghanna.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImplements detailsService;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("boaz").password(passWordEncoder().encode("boaz")).authorities("ROLE_ADMIN");
		// enable in memory based authentication with a user. Para usar este metodo, precisamos criar a classe UserDetailsServiceImplements
		auth.userDetailsService(detailsService);
	}
	
	@Bean
	public PasswordEncoder passWordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * http.authorizeRequests(), como vai ser a segurança das requisições
	 * anyRequest().authenticated(), para qualquer requisição o usuário precisa estar autenticado
	 * antMatchers("/usuario/logar").permitAll(), informa que na pagina usuário/logar todos estão permitidos na classe UsuarioController
	 * antMatchers("/usuario/financeiro").hasAnyRole("<usuario que terá permissão>"), este usuário terá uma classe com configuração AuthenticationManagerBuilder
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		//.antMatchers("/**").permitAll()
		.antMatchers(HttpMethod.POST, "/usuario/logar").permitAll() //caminho de autorização do meu controller
		.antMatchers(HttpMethod.POST, "/usuario/cadastrar").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors() //liberar o cors para não dar erro, vamos ver bastante no front end
		.and().csrf().disable(); //disable no csrf que é um tipo de ataque não podemos sofrer, mas não configuramos agora por ser mais complicado.
	}
}
