package com.pokemongo.pokemongo.bean

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*
import javax.transaction.Transactional

@Entity
@Table(name = "coordinate")
data class CoordinateBean(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_coordinate: Int,
    var long_coordinate: Double,
    var lat_coordinate: Double) {

    constructor() : this(0, 0.0,0.0)
}

data class ErrorBean(var message : String)




