package app.controller

import app.api.UserAPI
import app.entity.User
import app.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) : UserAPI {

    override fun addUser(chatId: String): User = userService.addUser(chatId)

    override fun deleteUser(chatId: String) = userService.deleteUser(chatId)
}