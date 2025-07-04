package app.service

import app.dto.MessageDTO

interface NotificationService {

    fun sendEmail(message: MessageDTO)
}