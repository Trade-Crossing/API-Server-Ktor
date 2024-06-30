package com.tradecrossing.service

import com.tradecrossing.domain.ResidentInfo
import com.tradecrossing.domain.ResidentInfoDto
import com.tradecrossing.dto.request.auth.RegisterRequest
import com.tradecrossing.repository.ResidentRepository
import com.tradecrossing.system.exceptions.ConfictException
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import io.ktor.server.plugins.*
import java.util.*

class AuthService(private val residentRepository: ResidentRepository) {

  suspend fun registerUser(id: UUID, request: RegisterRequest) {
    dbQuery {

      residentRepository.findbyId(id)?.apply { registered = true } ?: throw NotFoundException("존재하지 않는 유저입니다.")

      var residentInfo = ResidentInfo.findById(id)

      if (residentInfo == null) {
        residentInfo = ResidentInfo.new(id) {
          profilePic = request.profilePic
          username = request.username
          islandName = request.islandName
          introduction = request.introduction
        }
        println(residentInfo)
      } else {
        throw ConfictException("이미 등록된 유저입니다.")
      }
    }
  }

  suspend fun getResidentInfo(id: UUID) = dbQuery {
    val residentInfo =
      ResidentInfo.findById(id) ?: throw NotFoundException("존재하지 않는 유저입니다.")

    ResidentInfoDto(residentInfo)
  }

  suspend fun getIslandCode(id: UUID) = dbQuery {
    val residentInfo =
      ResidentInfo.findById(id) ?: throw NotFoundException("존재하지 않는 유저입니다.")

    residentInfo.islandCode
  }

  suspend fun updateIslandCode(id: UUID, islandCode: String) = dbQuery {
    val residentInfo =
      ResidentInfo.findById(id) ?: throw NotFoundException("존재하지 않는 유저입니다.")

    residentInfo.islandCode = islandCode
  }
}