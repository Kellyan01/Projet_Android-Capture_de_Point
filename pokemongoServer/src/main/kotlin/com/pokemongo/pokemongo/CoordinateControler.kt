package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
import com.pokemongo.pokemongo.bean.ErrorBean
import com.pokemongo.pokemongo.bean.LoginBean
import com.pokemongo.pokemongo.bean.UsersBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

val erreurClient = ErrorBean("Erreur du client HTTP")
val erreurServer = ErrorBean("Erreur du serveur HTTP")
val erreurInfo =  ErrorBean("En attente ...")



@RestController
class MyRestController(private val coordinateDAO: CoordinateDAO, private val usersDAO: UsersDAO) {

    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")
        return "helloWorld"
    }

    //http://localhost:8080/getCoordinate
    //Permet de recuperer les coordonées du flag
    //JSON recu: { "id_coordinate" : 1, "long_coordinate" : " ", "lat_coordinate": " " }
    @GetMapping("/getCoordinate")
    fun getCoordinate(response: HttpServletResponse): Any? {
        println("/getCoordinate ")
        return try {
            coordinateDAO.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
            return when (response.status) {
                in 400..499 -> {
                    erreurClient
                }
                in 500..599 -> {
                    erreurServer
                }
                in 100..199 -> {
                    erreurInfo
                }
                else -> {
                    return "Erreur inconnue"
                }
            }
        }
    }

    //http://localhost:8080/setCoordinate
    //Permet de creer les positions du flag et de les inscrire dans la DB
    //JSON envoyé : //JSON : { "id_coordinate" : 1, "long_coordinate" : " ", "lat_coordinate": " " }
    // le champ id_coordinate n'est pas necessaire, renvoie une erreur sur long ou lat sont null
    @PostMapping("/setCoordinate")
    fun setCoordinate(@RequestBody coordinate: CoordinateBean, response: HttpServletResponse) {
        println("/setCoordinate ")
        try {
            if (coordinate.lat_coordinate < 90 && coordinate.lat_coordinate > -90 && coordinate.long_coordinate < 180 && coordinate.long_coordinate > -180){
                coordinateDAO.save(coordinate)
            }else{
                println("Out of range")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 404

        }

    }


    //Enregistrement des joueurs(POST)/
    //http://localhost:8080/register
    //JSON : { "name" : "toto", "password": "motdepasse", "mail": "mon@mail.com" }
    @PostMapping("/register")
    fun register(@RequestBody user: UsersBean, response: HttpServletResponse): Any {
        println("/register name = " + user.name_users + ", password = " + user.password_users.hashCode() + ", mail = " + user.email_users)
        try {
            if (user.name_users.equals(usersDAO.findByName_users(user)) || user.email_users.equals(usersDAO.findByEmail_users(user)) ) {
                return "Identifiants déja utilisés"
            }else{
                user.password_users = user.password_users.hashCode().toString()
                usersDAO.save(user)
                return "enregistrement ok"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
            return erreurClient
        }
    }


    //Gestion de connexion(POST)//
    //http://localhost:8080/login
    //Permet à l'utilisateur de se connecter côté client, après vérification du serveur auprès de la DB.
    //JSON : { "name" : "toto", "password": "motdepasse” }
    @PostMapping("/login")
    fun login(@RequestBody login : LoginBean) {
        println(login)
    }

}








