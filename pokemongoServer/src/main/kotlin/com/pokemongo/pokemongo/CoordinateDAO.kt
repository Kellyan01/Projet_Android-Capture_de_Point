package com.pokemongo.pokemongo
import com.pokemongo.pokemongo.bean.CoordinateBean
import com.pokemongo.pokemongo.bean.UsersBean
import org.hibernate.cache.spi.support.AbstractReadWriteAccess
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CoordinateDAO:JpaRepository<CoordinateBean, Int> { //<Bean, Typage Id>


}
