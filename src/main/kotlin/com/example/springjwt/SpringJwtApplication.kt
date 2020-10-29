package com.example.springjwt

import com.example.springjwt.app.JwtService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class SpringJwtApplication {

    @Bean
    fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun getBCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }

//    @Bean
//    fun getJWTService(): JwtService {
//        return JwtService()
//    }
}

fun main(args: Array<String>) {
    runApplication<SpringJwtApplication>(*args)
}
