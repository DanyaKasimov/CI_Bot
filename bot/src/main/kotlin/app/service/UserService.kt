package app.service

import app.client.AppHttpClient
import app.dto.UserDTO
import org.springframework.stereotype.Service

@Service
class UserService(private val appHttpClient: AppHttpClient) {

    companion object {
        private const val USER_ENDPOINT = "/api/users"
    }

    fun register(chatId: String): UserDTO? {
        return appHttpClient.post(
            "$USER_ENDPOINT/$chatId",
            UserDTO::class.java,
            body = null)
            ?.body
    }

    fun delete(chatId: String) {
        appHttpClient.delete("$USER_ENDPOINT/$chatId", null)
    }
}