package com.example.springjwt

import com.example.springjwt.app.JwtService
import com.example.springjwt.app.filters.JWTAuthenticationFilter
import com.example.springjwt.app.filters.JWTAuthorizationFilter
import com.example.springjwt.app.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

  @Autowired
  private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

  @Autowired
  private lateinit var jwtService: JwtService

  override fun configure(http: HttpSecurity?) {
    if (http === null) {
      return
    }
    http.cors()
      .and()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(JWTAuthenticationFilter(authenticationManager(), jwtService))
      .addFilter(JWTAuthorizationFilter(authenticationManager(), jwtService))
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .csrf().disable()
  }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource {
    val source = UrlBasedCorsConfigurationSource()

    val corsConfiguration = CorsConfiguration().applyPermitDefaultValues()
    source.registerCorsConfiguration("/**", corsConfiguration)

    return source
  }
}