package app.service.notification

import app.client.HttpClient
import app.dto.MessageDTO
import app.service.NotificationService
import org.springframework.stereotype.Service

@Service
class NotificationHTTPService(private val httpClient: HttpClient) : NotificationService {

    override fun sendEmail(message: MessageDTO) {
        httpClient.sendBotNotification(message)
    }
}