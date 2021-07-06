package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


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
        fun getCoordinate(): CoordinateDAO {
            println("/getCoordinate ")

            return this.coordinateDAO
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
*/



