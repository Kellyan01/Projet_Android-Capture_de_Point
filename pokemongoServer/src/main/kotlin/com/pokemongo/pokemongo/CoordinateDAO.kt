package com.pokemongo.pokemongo
import com.pokemongo.pokemongo.bean.CoordinateBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository



@Repository
interface CoordinateDAO:JpaRepository<CoordinateBean, Int> {//<Bean, Typage Id>
   // fun findById_coordinate(name: String?): List<CoordinateBean?>
   // fun FindByNameContaining(name:String): List<CoordinateBean>

  //  @Transactional
  //  fun deleteByName(name:String):Long
}

