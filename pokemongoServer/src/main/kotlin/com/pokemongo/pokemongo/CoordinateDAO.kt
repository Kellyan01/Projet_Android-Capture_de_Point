package com.pokemongo.pokemongo
import com.pokemongo.pokemongo.bean.CoordinateBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Repository
interface CoordinateDAO:JpaRepository<CoordinateBean, Double> //<Bean, Typage Id>




