package com.pokemongo.pokemongo

import CoordinateBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


class RestControler {
    @RestController
    class MyRestController {
        //http://localhost:8080/test
        @GetMapping("/test")
        fun testMethode(): String {
            println("/test")
            return "helloWorld"
        }

        //http://localhost:8080/testJson
        @GetMapping("/testJson")
        fun StudentBeantestJson(): CoordinateBean {
            println("/testJson ")
            return CoordinateBean(1,12.00,12.00)
        }
    }
}