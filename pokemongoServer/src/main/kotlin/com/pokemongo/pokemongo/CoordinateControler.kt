package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
import com.pokemongo.pokemongo.bean.UsersBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


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
            response.status = 518
            null
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
            //TODO controle data

            coordinateDAO.save(coordinate)
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
        }

    }


    //Enregistrement des joueurs(POST)/
    //http://localhost:8080/register
    //JSON : { "id_user" : 1, "name" : "toto", "password": "motdepasse", "mail": "mon@mail.com" }
    @PostMapping("/register")
    fun register(@RequestBody users: UsersBean, response: HttpServletResponse) {
        println("/register")
        println("/login name = " + users.name_users + ", password = " + users.password_users + ", mail = " + users.email_users)
        try {
        usersDAO.save(users)
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
        }
    }


    //Gestion de connexion(POST)//

    //http://localhost:8080/login
    //Permet à l'utilisateur de se connecter côté client, après vérification du serveur auprès de la DB.
    //JSON : { "id_user" : 12, "name" : "toto", "password": "motdepasse” }
    //@PostMapping("/login")
    //fun login(@RequestBody user: UsersBean) {

    //}



}








