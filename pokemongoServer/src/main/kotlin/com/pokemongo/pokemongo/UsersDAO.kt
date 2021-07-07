package com.pokemongo.pokemongo
import com.pokemongo.pokemongo.bean.UsersBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersDAO: JpaRepository<UsersBean, Int> //<Bean, Typage Id>