package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
import com.pokemongo.pokemongo.bean.ErrorBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
class MyRestController(private val coordinateDAO: CoordinateDAO) {

    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")
        return "helloWorld"
    }

    //http://localhost:8080/getCoordinate
    //Permet de recuperer les coordonées du flag
    @GetMapping("/getCoordinate")
    fun getCoordinate(response: HttpServletResponse): Any? {
        println("/getCoordinate ")
         try {
            return coordinateDAO.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
            var error = ErrorBean("Error 518")
            return error
        }
    }


    //http://localhost:8080/setCoordinate
    //Permet de creer les positions du flag et de les inscrire dans la DB
    @PostMapping("/setCoordinate")
    fun setCoordinate(coordinate: CoordinateBean,response: HttpServletResponse): Any? {
        println("/setCoordinate ")
        try {
            coordinateDAO.save(coordinate)
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
            var error = ErrorBean("Error 518")
            return error
        }

    }
}

/*
@RestController
    class CoordinateDAO(private var coordinateDAO: CoordinateDAO) {

        var INCORRECT_VALUE = 203
        var ADD_USER = 201

    @GetMapping("/catchDB")
    fun find(name: String?, response: HttpServletResponse): List<CoordinateBean>? {
        println("/find name=$name")

        if (name.isNullOrBlank()) {
            response.status = INCORRECT_VALUE
            return null
        }
        val coordinateList = coordinateDAO.findById_coordinate(name)

        if (coordinateList.isEmpty()) {
            response.status = ADD_USER
            return null
        }

        return coordinateList.map { CoordinateBean(it!!.id_coordinate, it.long_coordinate, it.lat_coordinate) }
    }
}



        //Enregistrement des joueurs(POST)//

        //http://localhost:8080/register
        //Permet d'accéder au formulaire d’enregistrement, ces données seront ensuite envoyées vers le serveur afin d’enregistrer l’utilisateur dans la DB.
        //JSON : { "id_user" : 1, "name" : "toto", "password": "motdepasse”, "mail": "mon@mail.com” }
        //@PostMapping("/register")
        //fun register(@RequestBody user: UserBean) {
            //println("/login name=$name, password=$password, mail=$mail")

            //}
        //}

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



