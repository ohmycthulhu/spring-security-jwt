package com.example.springjwt.app.filters

import com.example.springjwt.SecurityConstants
import com.example.springjwt.app.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Service
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authenticationManager: AuthenticationManager, private val jwtService: JwtService)
  : BasicAuthenticationFilter(authenticationManager) {


  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    val header = request.getHeader(SecurityConstants.HEADER_STRING)

    if (header !== null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      val authentication = getAuthentication(request)

      SecurityContextHolder.getContext().authentication = authentication
    }
    chain.doFilter(request, response)
  }

  private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
    val token = request.getHeader(SecurityConstants.HEADER_STRING)

    if (token === null) return null

    val user = jwtService.getUser(token.replace(SecurityConstants.TOKEN_PREFIX, ""))

    if (user === null) {
      return null
    }

    return UsernamePasswordAuthenticationToken(user, null, arrayListOf())
  }
}