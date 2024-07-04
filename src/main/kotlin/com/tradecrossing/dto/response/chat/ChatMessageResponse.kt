import com.tradecrossing.domain.ChatMessage
import com.tradecrossing.types.LocalDateTimeSerializer
import com.tradecrossing.types.UUIDSerializer
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
@Schema(name = "ChatMessage", description = "Chat message response")
data class ChatMessageResponse(
  val id: Long,
  @Serializable(with = UUIDSerializer::class)
  val sender: UUID,
  val message: String,
  @Serializable(with = LocalDateTimeSerializer::class)
  val sendAt: LocalDateTime
) {
  constructor(chatMessage: ChatMessage) : this(
    chatMessage.id.value,
    chatMessage.senderId.value,
    chatMessage.message,
    chatMessage.sendAt
  )
}