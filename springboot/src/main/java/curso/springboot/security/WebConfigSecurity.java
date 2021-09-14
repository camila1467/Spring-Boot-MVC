package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
//implementar em memoria a seguranca que vamos fazer aqui
	
	
	@Autowired
	private ImplementacaoUserDetailsService impementacaoUserdetailsService;
	
	
	@Override
	protected void configure (HttpSecurity http)throws Exception {
	
		
		
		http.csrf()
		.disable()//desativa as configuracoes padrao de momeria do spring
		.authorizeRequests()//permitir restringir acessos
		.antMatchers(HttpMethod.GET ,"/").permitAll()
		.antMatchers(HttpMethod.GET ,"/cadastropessoa").hasAnyRole("ADMIN")//permiti qualquer usuario acessar a pagina index
	.anyRequest().authenticated()
	.and().formLogin().permitAll()//permite qualquer usuario
	.loginPage("/login")
	.defaultSuccessUrl("/cadastropessoa")
	.failureUrl("/login?error=true")
	.and()
	.logout().logoutSuccessUrl("/login")//mapeia url de logout e valida usuario autenticado
	
	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		

	}
	@Override//cria autenticacao do usuario com banco de dados ou em memoria
	protected void configure (AuthenticationManagerBuilder auth)throws Exception
	{
		//auth.userDetailsService(impementacaoUserdetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
		
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("camila")
		.password("$2a$10$7W.COCxv/B7GJyEgsk2Kuur/uqAIWxOmSGD5QY44fnjrNPkNrRAha")
		.roles("ADMIN");
		
	}
	@Override//
	public void configure(WebSecurity web )throws Exception{
		web.ignoring().antMatchers("/materialize/**");
	}
	
	
}
