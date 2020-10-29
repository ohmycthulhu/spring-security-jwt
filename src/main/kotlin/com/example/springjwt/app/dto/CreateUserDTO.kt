package com.example.springjwt.app.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

class CreateUserDTO (
  val username: String = "",
  var password: String = ""
) : Serializable