package app.state

import org.springframework.stereotype.Component
import java.util.*


@Component
class StateHandler {
    private val chatActive: MutableMap<String, Boolean> = HashMap()

    fun setActive(chatId: String) {
        chatActive[chatId] = true
    }

    fun deleteActive(chatId: String) {
        chatActive.remove(chatId)
    }

    fun isActive(chatId: String): Boolean {
        return chatActive.containsKey(chatId)
    }
}