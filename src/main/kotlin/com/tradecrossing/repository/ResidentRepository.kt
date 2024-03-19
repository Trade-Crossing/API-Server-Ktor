package com.tradecrossing.repository

import com.tradecrossing.domain.entity.resident.ResidentEntity
import com.tradecrossing.domain.entity.resident.ResidentInfoEntity
import java.util.*

class ResidentRepository {
  fun findbyId(id: UUID): ResidentEntity? = ResidentEntity.findById(id)

  fun findResidentInfoById(id: UUID): ResidentInfoEntity? = ResidentInfoEntity.findById(id)


}