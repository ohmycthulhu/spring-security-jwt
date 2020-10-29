package com.example.springjwt.app.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class AuthenticationInfoDTO (
    val token: String,
    val userInfo: UserInfoDTO
)