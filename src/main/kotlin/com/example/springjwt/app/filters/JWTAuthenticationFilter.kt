package com.example.springjwt.app.filters

import com.example.springjwt.SecurityConstants
import com.example.springjwt.app.JwtService
import com.example.springjwt.app.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter (am: AuthenticationManager, private val jwtService: JwtService)
  : UsernamePasswordAuthenticationFilter() {

//  private var authenticationManager: AuthenticationManager

//  @Autowired
//  lateinit var jwtService: JwtService

  init {
    this.authenticationManager = am
    setFilterProcessesUrl("/api/services/controller/user/login")
  }

  override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

    try {
      val header = request?.getHeader(SecurityConstants.HEADER_STRING) ?: ""
      val user = jwtService.getUser(header.replace(SecurityConstants.TOKEN_PREFIX, ""))
      return authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken(
          user?.username,
          user?.password,
          arrayListOf()
        )
      )
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
    val token = jwtService.createLoginToken(authResult?.principal as User)
    response?.addHeader(SecurityConstants.HEADER_STRING, "${SecurityConstants.TOKEN_PREFIX}$token")
  }
}