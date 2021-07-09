package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
import com.pokemongo.pokemongo.bean.ErrorBean
import com.pokemongo.pokemongo.bean.LoginBean
import com.pokemongo.pokemongo.bean.UsersBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
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
            // return la liste de tous les points présents dans la DB
            coordinateDAO.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 517
            return ErrorBean("Erreur : " + e.message)
        }
    }

    //http://localhost:8080/setCoordinate
    //Permet de creer les positions du flag et de les inscrire dans la DB
    //JSON envoyé : //JSON : { "id_coordinate" : 0, "long_coordinate" : " ", "lat_coordinate": " " }
    // le champ id_coordinate n'est pas necessaire, renvoie une erreur sur long ou lat sont null
    @PostMapping("/setCoordinate")
    fun setCoordinate(@RequestBody coordinate: CoordinateBean, response: HttpServletResponse): ErrorBean? {
        println("/setCoordinate ")
        return try {
            // Condition empechant la création de points GPS ayant des valeurs abberantes
            if (coordinate.lat_coordinate > 90 || coordinate.lat_coordinate < -90 || coordinate.long_coordinate > 180 ||coordinate.long_coordinate < -180){
                throw Exception("Coordonnées out of range")
            }
            coordinateDAO.save(coordinate)
            null
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
            ErrorBean("Erreur : " + e.message)
        }
    }

    //Enregistrement des joueurs(POST)/
    //http://localhost:8080/register
    //JSON : { "name_users" : "toto", "password_users": "motdepasse", "email_users": "mon@mail.com" }
    @PostMapping("/register")
    fun register(@RequestBody user: UsersBean, response: HttpServletResponse) :Any? {
        println("/register name = " + user.name_users + ", password = " + user.password_users.hashCode() + ", mail = " + user.email_users)
        // requetes SQL pour trouver sur le nom et le mail du user existe dans la DB
        val checkUserName = usersDAO.findByName_users(user.name_users)
        val checkUserMail = usersDAO.findByEmail_users(user.email_users)
        return try {
            // Si les 2 champs n'existent pas, l'enregistrement peut s'effectuer
            if (checkUserName!=null && checkUserMail!=null) {
                throw Exception("Identifiants déjà utilisés")
            }
            // Si les 1 des 2 ||les 2 champs existent dans la DB =>
            user.password_users = user.password_users.hashCode().toString()
            usersDAO.save(user)
            "enregistrement ok"
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 519
            ErrorBean("Erreur : " + e.message)
        }
    }

    //Gestion de connexion(POST)//
    //http://localhost:8080/login
    //Permet à l'utilisateur de se connecter côté client, après vérification du serveur auprès de la DB.
    //JSON : { "name_users" : "toto", "password_users": "motdepasse" }
    @PostMapping("/login")
    fun login(@RequestBody login : LoginBean, response: HttpServletResponse): Any? {
        println("/login name = " + login.name_users + ", password = " + login.password_users.hashCode())
        // ...
        try {
            val checkUserName = usersDAO.findByName_users(login.name_users) ?: throw Exception("Incorrect username or password.")
            // Vérification du nom

            if (login.password_users.hashCode().toString() != checkUserName.password_users) {
                throw Exception("Incorrect username and/or password")
            }
            // Génération d'un Id de Session
            checkUserName.idsession_users =  UUID.randomUUID().toString()
            usersDAO.save(checkUserName)
            println(checkUserName.idsession_users)
            return checkUserName.idsession_users
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 519
            return ErrorBean("Erreur : " + e.message)
        }
    }

}








