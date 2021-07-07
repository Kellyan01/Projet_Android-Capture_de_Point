package com.pokemongo.pokemongo

import com.pokemongo.pokemongo.bean.CoordinateBean
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
            return null
        }
    }


    //http://localhost:8080/setCoordinate
    //Permet de creer les positions du flag et de les inscrire dans la DB
    @PostMapping("/setCoordinate")
    fun setCoordinate(coordinate: CoordinateBean,response: HttpServletResponse) {
        println("/setCoordinate ")
        try {
            coordinateDAO.save(coordinate)
        } catch (e: Exception) {
            e.printStackTrace()
            response.status = 518
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
*/



