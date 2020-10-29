package com.example.springjwt.app.controllers

import com.example.springjwt.app.JwtService
import com.example.springjwt.app.dto.AuthenticationInfoDTO
import com.example.springjwt.app.dto.CreateUserDTO
import com.example.springjwt.app.dto.UserInfoDTO
import com.example.springjwt.app.models.User
import com.example.springjwt.app.repository.UsersRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class UsersController {

  @Autowired
  lateinit var objectMapper: ObjectMapper

  @Autowired
  lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

  @Autowired
  lateinit var usersRepository: UsersRepository

  @Autowired
  lateinit var jwtService: JwtService

  @PostMapping("/users")
  fun createUser(@RequestBody userDTO: CreateUserDTO): AuthenticationInfoDTO {

    userDTO.password = bCryptPasswordEncoder.encode(userDTO.password)
    val createdUser = usersRepository.save(objectMapper.convertValue(userDTO, User::class.java))

    return AuthenticationInfoDTO(
      jwtService.createLoginToken(createdUser),
      objectMapper.convertValue(createdUser, UserInfoDTO::class.java)
    )
  }

  @GetMapping("/user")
  fun getCurrentUser(@RequestHeader(name = "Authorization") authentication: String): UserInfoDTO {
    val user = SecurityContextHolder.getContext().authentication.principal as User
    return objectMapper.convertValue(user, UserInfoDTO::class.java)
  }

}