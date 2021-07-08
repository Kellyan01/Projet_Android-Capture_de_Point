package com.pokemongo.pokemongo.bean
import javax.persistence.*


@Entity
@Table(name = "coordinate")
data class CoordinateBean(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_coordinate: Int,
    var long_coordinate: Double,
    var lat_coordinate: Double) {

    constructor() : this(0, 0.0,0.0)
}



@Entity
@Table(name = "users")
data class UsersBean(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_users: Int,
    var name_users: String,
    var password_users: String,
    var email_users: String) {

    constructor() : this(0, "","", "")
}

data class ErrorBean(var message : String)

data class LoginBean(var name_users: String, var password_users: String)







