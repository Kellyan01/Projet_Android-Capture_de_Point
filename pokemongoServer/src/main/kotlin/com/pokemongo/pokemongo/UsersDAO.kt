package com.pokemongo.pokemongo
import com.pokemongo.pokemongo.bean.UsersBean
import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsersDAO: JpaRepository<UsersBean, Int> {//<Bean, Typage Id> {

    @Query("from UsersBean where name_users= ?1 AND email_users=?2")
    fun findByEmail_usersAndPassword_users(email:String, passWord : String) : UsersBean?

    @Query("from UsersBean where  name_users= ?1 AND email_users=?2 ")
    fun findByName_usersOrEmail_users(name: String, email: String): UsersBean?

    @Query("from UsersBean where  name_users= ?1")
    fun findByName_users(name: String): UsersBean?

    @Query("from UsersBean where email_users= ?1")
    fun findByEmail_users(mail: String): UsersBean?
}
