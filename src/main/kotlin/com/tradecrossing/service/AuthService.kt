package com.tradecrossing.service

import com.tradecrossing.domain.entity.ResidentInfoEntity
import com.tradecrossing.domain.tables.ResidentInfoTable
import com.tradecrossing.dto.request.auth.RegisterRequest
import com.tradecrossing.repository.ResidentRepository
import com.tradecrossing.system.exceptions.ConfictException
import com.tradecrossing.system.plugins.DatabaseFactory.dbQuery
import io.ktor.server.plugins.*
import java.util.*

class AuthService(private val residentRepository: ResidentRepository) {


  suspend fun registerUser(id: UUID, request: RegisterRequest) {
    dbQuery {
      residentRepository.findbyId(id) ?: throw NotFoundException("존재하지 않는 유저입니다.")

      var residentInfo = ResidentInfoTable.select(ResidentInfoTable.columns).where {
        ResidentInfoTable.id eq id
      }.singleOrNull()?.let {
        ResidentInfoEntity.wrapRow(it)
      }

      if (residentInfo == null) {
        residentInfo = ResidentInfoEntity.new(id) {
          profilePic = request.profilePic
          username = request.username
          islandName = request.islandName
          introduction = request.introduction
          defaultProfile = request.defaultProfile
        }
        println(residentInfo)
      } else {
        throw ConfictException("이미 등록된 유저입니다.")
      }
    }
  }
}