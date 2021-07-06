package com.pokemongo.pokemongo

import CoordinateBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


    @RestController
    class MyRestController {
        //http://localhost:8080/test
        @GetMapping("/test")
        fun testMethode(): String {
            println("/test")
            return "helloWorld"
        }

        //http://localhost:8080/getCoordinate
        //Permet de recuperer les coordonées du flag
        @GetMapping("/getCoordinate")
        fun StudentBeantestJson(): CoordinateBean {
            println("//getCoordinate ")

            return CoordinateBean(1,12.1240,12.02133)
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

        //Envoi de la position du client(POST)//

        //http://localhost:8080/setCoordinate
        //Permet d’envoyer les coordonnées de la position du client
        //JSON : { "id_coordinate" : 1, "lat_coordinate" : 41.40338, "long_coordinate" : 2.17403 }
        @PostMapping("/setCoordinate")
        fun receiveJson( @RequestBody coordinate: CoordinateBean ): CoordinateBean {
            println("/testSetCoordinate : Longitude= " + coordinate.long_coordinate)
            println("/testSetCoordinate : Latitude= " + coordinate.lat_coordinate)
            return coordinate
        }

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


