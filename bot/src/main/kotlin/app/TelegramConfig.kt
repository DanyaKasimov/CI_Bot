package app

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramConfig(
    private val bot: CoreTelegramBot
) {

    @PostConstruct
    fun init() {
        TelegramBotsApi(DefaultBotSession()).registerBot(bot)
    }
}