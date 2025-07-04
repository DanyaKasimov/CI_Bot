package app.repository

import app.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    fun existsByChatId(chatId: String): Boolean

    fun findByChatId(chatId: String): User?
}