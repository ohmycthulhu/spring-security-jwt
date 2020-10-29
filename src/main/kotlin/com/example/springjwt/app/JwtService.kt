package com.example.springjwt.app

import com.example.springjwt.SecurityConstants
import com.example.springjwt.app.models.User
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val DATA_KEY = "user"

    private val key: ByteArray
    get () = SecurityConstants.SECRET.toByteArray()

    fun getUser(jwt: String): User? {
        val claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
        return objectMapper.convertValue(claims.body[this.DATA_KEY], User::class.java)
    }

    fun createLoginToken(user: User): String {
        val curTime = System.currentTimeMillis()
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setExpiration(Date(curTime + SecurityConstants.EXPIRATION_TIME))
                .setIssuedAt(Date(curTime))
                .claim(DATA_KEY, user)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
    }
}