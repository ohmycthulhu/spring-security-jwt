package com.example.springjwt.app.repository

import com.example.springjwt.app.dto.CreateUserDTO
import com.example.springjwt.app.models.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import javax.transaction.Transactional

interface UsersRepository : JpaRepository<User, Long> {
  fun findByUsername(username: String): Optional<User>
}