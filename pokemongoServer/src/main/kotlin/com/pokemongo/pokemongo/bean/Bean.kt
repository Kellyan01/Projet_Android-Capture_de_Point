package com.pokemongo.pokemongo.bean

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.persistence.*

@Entity
@Table(name = "pokemongodb")
data class CoordinateBean(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_coordinate: Int,
    var long_coordinate: Double,
    var lat_coordinate: Double) {
    constructor() : this(0, 0.0,0.0)
}



