package com.pokemongo.pokemongo
import com.pokemongo.pokemongo.bean.UsersBean
import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsersDAO: JpaRepository<UsersBean, Int> {//<Bean, Typage Id> {
    //fun findByEmail_usersAndPassword_users(email:String, passWord : String) : UsersBean?

  //  @Query("from UsersBean where email_users = ")
    //fun checkUsers(email:String, passWord : String) : UsersBean?

    @Query("from UsersBean where name_users= ?1")
    fun findUsersBeanByName_users(name: String?): UsersBean?
}
