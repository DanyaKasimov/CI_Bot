package app.service

import app.entity.User
import app.repository.RepoRepository
import app.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val repoRepository: RepoRepository) {

    fun addUser(chatId: String): User {
        if (userRepository.existsByChatId(chatId)) throw RuntimeException("Чат уже зарегистрирован")

        return userRepository.save(User().apply {
            this.chatId = chatId
        })
    }

    fun deleteUser(chatId: String) {
        val user = getUser(chatId)
        repoRepository.deleteAllByUser(user)
        userRepository.delete(user)
    }

    fun getUser(chatId: String): User = userRepository.findByChatId(chatId)
        ?: throw RuntimeException("Пользователь не найден")

}