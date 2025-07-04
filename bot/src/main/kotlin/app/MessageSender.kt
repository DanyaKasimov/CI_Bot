package app

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Component

@Component
class MessageSender(private val bot: TelegramBot) {

    fun send(chatId: String?, message: String?) {
        bot.execute(SendMessage(chatId, message))
    }
}