package com.example.springjwt.app.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "users")
class User(var username: String = "",
           var password: String = "") {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    override fun toString(): String {
        return "User [userId=${id},username=${username},password=${password}]"
    }
}