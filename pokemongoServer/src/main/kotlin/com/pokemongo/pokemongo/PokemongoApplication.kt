package com.pokemongo.pokemongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class PokemongoApplication

fun main(args: Array<String>) {
    runApplication<PokemongoApplication>(*args)
}

@RestController
class MyRestController {
    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")
        return "helloWorld"
    }
}

