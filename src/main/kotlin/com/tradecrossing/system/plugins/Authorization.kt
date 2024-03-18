package com.tradecrossing.system.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.tradecrossing.dto.response.TokenResponse
import com.tradecrossing.system.exceptions.UnauthorizedException
import com.tradecrossing.types.TokenType
import com.tradecrossing.utils.toDate
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import io.netty.util.internal.logging.Log4J2LoggerFactory
import java.time.LocalDateTime
import java.util.*


internal val log = Log4J2LoggerFactory.getInstance("JwtAuthPlugin")

fun Application.generateJwtToken(userId: UUID): TokenResponse {
  val secret = environment.config.property("jwt.secret").getString()

  val accessToken = JWT.create()
    .withSubject(userId.toString())
    .withExpiresAt(
      LocalDateTime.now().plusDays(1).toDate()
    )
    .withClaim("type", TokenType.ACCESS.name)
    .sign(Algorithm.HMAC256(secret))

  val refreshToken = JWT.create()
    .withSubject(userId.toString())
    .withExpiresAt(
      LocalDateTime.now().plusMonths(3).toDate()
    )
    .withClaim("type", TokenType.REFRESH.name)
    .sign(Algorithm.HMAC256(secret))

  return TokenResponse(accessToken, refreshToken)
}

fun Route.authenticate(tokenType: TokenType, route: Route.() -> Unit): Route {
  val child = createChild(AuthRouter(tokenType)).apply {
    install(JwtAuthPlugin) { this.tokenType = tokenType }
    route()
  }
  return child
}

// 유저 ID를 입력
private val userIdKey = AttributeKey<UUID>("userId")
fun ApplicationCall.getUserId(): UUID = attributes[userIdKey]

class AuthRouter(private val tokenType: TokenType) : RouteSelector() {
  override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
    RouteSelectorEvaluation.Constant

  override fun toString(): String {
    return "(${tokenType.name})"
  }
}

class AuthPluginConfiguration {
  lateinit var tokenType: TokenType
}

val JwtAuthPlugin = createRouteScopedPlugin(
  name = "JwtAuthPlugin",
  createConfiguration = ::AuthPluginConfiguration
) {
  pluginConfig.apply {
    val config = applicationConfig!!
    val jwtSecret = config.property("jwt.secret").getString()
    val jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build()

    onCall { call ->
      val token = call.request.authorization()?.removePrefix("Bearer ") ?: throw UnauthorizedException()
      val jwt = try {
        jwtVerifier.verify(token)
      } catch (e: JWTVerificationException) {
        log.error(e.message)
        throw UnauthorizedException()
      }

      if (tokenType != TokenType.valueOf(jwt.getClaim("type").asString())) {
        throw UnauthorizedException()
      }

      val userId = UUID.fromString(jwt.subject)
      call.attributes.put(userIdKey, userId)
    }
  }
}