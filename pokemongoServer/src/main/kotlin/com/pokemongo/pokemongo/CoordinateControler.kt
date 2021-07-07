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
    //JSON : { "id_coordinate" : 1, "long_coordinate" : " ", "lat_coordinate": " ” }
    @GetMapping("/getCoordinate")
    fun getCoordinate(response: HttpServletResponse): Any? {
        println("/getCoordinate ")
        try {
            return coordinateDAO.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
            return null
        }
    }

    //http://localhost:8080/setCoordinate
    //Permet de creer les positions du flag et de les inscrire dans la DB
    @PostMapping("/setCoordinate")
    fun setCoordinate(coordinate: CoordinateBean, response: HttpServletResponse) {
        println("/setCoordinate ")
        try {
            coordinateDAO.save(coordinate)
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
        }

    }


    //Enregistrement des joueurs(POST)/
    //http://localhost:8080/register
    //JSON : { "id_user" : 1, "name" : "toto", "password": "motdepasse”, "mail": "mon@mail.com” }
    @PostMapping("/register")
    fun register(@RequestBody users: UsersBean) {
        println("/login name = " + users.name_users + ", password = " + users.password_users + ", mail = " + users.email_users)
        usersDAO.save(users)
    }
}


/*
        //Gestion de connexion(POST)//

        //http://localhost:8080/login
        //Permet à l'utilisateur de se connecter côté client, après vérification du serveur auprès de la DB.
        //JSON : { "id_user" : 12, "name" : "toto", "password": "motdepasse” }
        //@PostMapping("/login")
        //fun login(@RequestBody) {        }

        //Récupération de la liste de points générés(GET)//

        //http://localhost:8080/getCoordinate
        //Permet de récupérer les points générés par le serveur
        //@GetMapping("/getCoordinate")
        //fun find():  { }

        //Gestion des profils(GET)//

        //http://localhost:8080/profil
        //Permet d'accéder à son profil depuis le client
        @GetMapping("/profil")
        fun profil(){ }

    }

 */



