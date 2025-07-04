package app.config

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.BotCommand
import com.pengrad.telegrambot.request.SetMyCommands
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfig(private val appConfig: AppConfig) {

    @Bean
    fun telegramBot(): TelegramBot {
         return TelegramBot(appConfig.telegram.token)
    }

    @PostConstruct
    fun registerCommands() {
        val commands = arrayOf(
            BotCommand("/start", "Запустить бота"),
        )
        telegramBot().execute(SetMyCommands(*commands))
    }
}