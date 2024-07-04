import com.tradecrossing.domain.ChatMessage
import java.time.LocalDateTime
import java.util.*

data class ChatMessageResponse(
  val id: Long,
  val sender: UUID,
  val message: String,
  val sendAt: LocalDateTime
) {
  constructor(chatMessage: ChatMessage) : this(
    chatMessage.id.value,
    chatMessage.senderId.value,
    chatMessage.message,
    chatMessage.sendAt
  )
}