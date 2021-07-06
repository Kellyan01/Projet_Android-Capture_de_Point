package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
import org.springframework.web.bind.annotation.GetMapping
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

            return CoordinateBean(0,12.1240,12.02133)
        }
    }

    //    //http://localhost:8080/setCoordinate
    //    //Permet d’envoyer les coordonnées de la position du client
    //    @PostMapping("/setCoordinate")
    //    fun receiveJson(
    //        @RequestBody coordinate: com.pokemongo.pokemongo.Bean.CoordinateBean
    //    ): com.pokemongo.pokemongo.Bean.CoordinateBean {
    //        println("/testSetCoordinate : Longitude= " + coordinate.long_coordinate)
    //        println("/testSetCoordinate : Latitude= " + coordinate.lat_coordinate)
    //        return coordinate
    //    }



