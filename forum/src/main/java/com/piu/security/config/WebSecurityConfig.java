package com.piu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.piu.security.entrypoint.UnauthorizedEntryPoint;
import com.piu.security.filter.ValidationFilter;
import com.piu.security.handler.MyAuthenticationFailureHandler;
import com.piu.security.handler.MyAuthenticationSuccessHandler;
import com.piu.security.handler.MyLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	/**
	 * 注销处理器
	 * @return
	 */
	@Bean
	MyLogoutSuccessHandler myLogoutSuccessHandler() {
	    return new MyLogoutSuccessHandler();
	}
	/**
	 * 登录失败处理器
	 * @return
	 */
	@Bean
	MyAuthenticationFailureHandler myAuthenticationFailureHandler() {
	    return new MyAuthenticationFailureHandler();
	}
	/**
	 * 登录成功处理器
	 * @return
	 */
	@Bean
	MyAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
	    return new MyAuthenticationSuccessHandler();
	}
	/**
	 * 验证码拦截器
	 * @return
	 * @throws Exception
	 */
	@Bean
	ValidationFilter getValidationFilter() throws Exception {
		ValidationFilter validationFilter = new ValidationFilter();
		validationFilter.setAuthenticationManager(super.authenticationManager());
		validationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler());
		validationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler());
	    return validationFilter;
	}
	/*
	 * 取消密码的加密
	 */
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	/*
	 * 自定义UserDetailsAdapter
	 */
	@Bean
	UserDetailsService customUserService(){
		return new com.piu.sys.service.CustomUserService();
	}
	/*
	 * 将数据库中的用户的信息放去AuthenticationManagerBuilder中
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder()); 

    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint());
		//确保我们应用中的所有请求都需要用户被认证
		http.authorizeRequests()
		/*.antMatchers("/static/**","/asserts/**","/js/**").permitAll()
		.antMatchers("/templates/**").authenticated();*/
		.anyRequest().permitAll();
		//允许用户进行基于表单的认证
		http.formLogin()
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/login", true)
		.permitAll();
		//关闭 CSRF 保护  
		http.csrf().disable();
		//允许用户使用HTTP基于验证进行认证
		http.httpBasic();
		//注销行为任意访问
		http.logout().logoutUrl("/logout").logoutSuccessHandler(myLogoutSuccessHandler()).permitAll(); 
		
		http.addFilterAt(getValidationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	/**
	 * 配置静态资源的访问
	 */
	public void configure(WebSecurity web) throws Exception {
	    // 忽略URL
	    web.ignoring().antMatchers("/**/*.js", "/**/*.css", "/**/*.map", "/**/*.html","/**/*.jpg",
	            "/**/*.png");
	    
	}
}