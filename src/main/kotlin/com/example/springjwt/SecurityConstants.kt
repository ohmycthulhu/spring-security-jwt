package com.example.springjwt

class SecurityConstants {
  companion object {
    val SECRET: String = "password_string"
    val EXPIRATION_TIME = 900_000L
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    val SIGN_UP_URL = "/users"
  }
}