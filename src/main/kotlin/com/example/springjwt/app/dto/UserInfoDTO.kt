package com.example.springjwt.app.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class UserInfoDTO(
    val id: Long = 0,
    val username: String = ""
//    val password: String = ""
) : Serializable