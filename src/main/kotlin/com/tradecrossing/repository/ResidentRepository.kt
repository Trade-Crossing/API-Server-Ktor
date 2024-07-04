package com.tradecrossing.repository


import com.tradecrossing.domain.Resident
import com.tradecrossing.domain.ResidentInfo
import java.util.*

class ResidentRepository {
  fun findbyId(id: UUID): Resident? = Resident.findById(id)

  fun findResidentInfoById(id: UUID): ResidentInfo? = ResidentInfo.findById(id)
}