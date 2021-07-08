package com.pokemongo.flagcatcher.model.beans

data class UserBean  ( val id_users: Int = 0,  val name_users: String, val password_users: String, val email_users: String)

data class LoginBean (val name_users: String, val password_users: String)