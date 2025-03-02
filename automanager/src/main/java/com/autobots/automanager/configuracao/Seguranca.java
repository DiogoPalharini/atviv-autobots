package com.autobots.automanager.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.autobots.automanager.adaptadores.UserDetailsServiceImpl;
import com.autobots.automanager.filtros.Autenticador;
import com.autobots.automanager.filtros.Autorizador;
import com.autobots.automanager.jwt.ProvedorJwt;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize
public class Seguranca extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl servico;

	@Autowired
	private ProvedorJwt provedorJwt;

	private static final String[] rotasPublicas = {
			"/auth/login", // Login
			"/cadastrar-usuario" // Cadastro de usuário
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.authorizeRequests()
				.antMatchers(rotasPublicas).permitAll() // Permitir acesso às rotas públicas
				.antMatchers("/clientes/listar").hasRole("ADMIN") // Apenas ADMIN pode acessar
				.antMatchers("/usuarios/listar").hasAnyRole("ADMIN", "MANAGER") // ADMIN e MANAGER podem acessar
				.anyRequest().authenticated() // Todas as outras rotas exigem autenticação
				.and()
				.addFilter(new Autenticador(authenticationManager(), provedorJwt))
				.addFilter(new Autorizador(authenticationManager(), provedorJwt, servico));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Sem estado
	}

	@Override
	protected void configure(AuthenticationManagerBuilder autenticador) throws Exception {
		autenticador.userDetailsService(servico).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuracao = new CorsConfiguration().applyPermitDefaultValues();
		configuracao.addAllowedHeader("*");
		configuracao.addAllowedMethod("*");
		configuracao.addAllowedOrigin("*");
		UrlBasedCorsConfigurationSource fonte = new UrlBasedCorsConfigurationSource();
		fonte.registerCorsConfiguration("/**", configuracao);
		return fonte;
	}
}
