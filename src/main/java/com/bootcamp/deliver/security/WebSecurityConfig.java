package com.bootcamp.deliver.security;

import java.util.Arrays;

import com.bootcamp.deliver.Service.MemberUserDetailsService;

// import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  AuthenticationSuccessHandler successHandler;

  @Bean
  public UserDetailsService userDetailsService() {
    return new MemberUserDetailsService();
  }

  @Bean
  public BCryptPasswordEncoder passwordencoder() {
    return new BCryptPasswordEncoder();
  }

  // @Override
  // protected void configure(HttpSecurity http) throws Exception {
  //   http.cors().and().csrf().disable();
  // }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordencoder());

    return provider;
  }

  // first one
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    http.authorizeRequests()
        .antMatchers("/myorders", "/mycart", "/userproduct_list", "/user_list", "/product_list", "/allorders",
            "/profile", "/profileAdmin", "/shipping_list")
        .authenticated()
        // .antMatchers("/admin", "/userdashboard").authenticated()
        
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/product_list")
        .permitAll()
        .and()
        // .logout().logoutSuccessUrl("/").permitAll();
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/");
  }

  // @Override
  // public void configure(AuthenticationManagerBuilder auth) throws Exception {
  // auth.inMemoryAuthentication()
  // .withUser("user").password("{noop}password").roles("user")
  // .and()
  // .withUser("Admin").password("{noop}password").roles("Admin");
  // }

  // @Override
  // public void configure(HttpSecurity http) throws Exception {
  // http
  // .csrf().disable()
  // .authorizeRequests()
  // .antMatchers("/user").hasAnyRole("user")
  // .antMatchers("/Admin").hasAnyRole("Admin")
  // .and().formLogin().loginPage("/login")
  // .successHandler(successHandler)
  // .permitAll()
  // .and().logout();
  // http.authorizeRequests()
  // .antMatchers("/list").hasRole("user")
  // .antMatchers("/home").hasRole("Admin")
  // .antMatchers("/list").authenticated()
  // .anyRequest().permitAll()
  // .and()
  // .formLogin().usernameParameter("email").successHandler(successHandler).permitAll()
  // .and()
  // .logout().logoutSuccessUrl("/").permitAll();
  // }

  // @Override
  // protected void configure(HttpSecurity http) throws Exception {
  // http
  // .authorizeRequests()
  // .antMatchers("/css/**").permitAll()
  // .antMatchers("/home").hasAuthority("Admin")
  // .anyRequest().authenticated()
  // .and()
  // .formLogin().usernameParameter("email")
  // // .loginPage("/login")
  // .successHandler(successHandler)
  // .permitAll()
  // .and()
  // .logout()
  // .permitAll()
  // .and().csrf().disable(); // we'll enable this in a later blog post
  // }

  // @Autowired
  // public void configureGlobal(AuthenticationManagerBuilder auth) throws
  // Exception {
  // auth
  // .inMemoryAuthentication()
  // .withUser("user").password("{noop}pass").roles("user")
  // .and()
  // .withUser("Admin").password("{noop}pass").roles("Admin");
  // }
}
