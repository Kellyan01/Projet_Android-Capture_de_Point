package com.pokemongo.pokemongo
import CoordinateBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CoordinateDAO: JpaRepository<CoordinateBean, Long> {// <Bean, Typage Id>
 }