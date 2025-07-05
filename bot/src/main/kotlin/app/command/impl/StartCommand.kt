package app.command.impl

import app.MessageSender
import app.command.Command
import app.constants.CommandType
import app.service.UserService
import app.state.StateHandler
import org.springframework.stereotype.Component

@Component
class StartCommand(private val userService: UserService,
                   private val messageSender: MessageSender,
                   private val stateHandler: StateHandler) : Command {

    override fun isSupported(command: String): Boolean {
        return CommandType.START.description.equals(command, ignoreCase = true)
    }

    override fun execute(message: String) {
        try {
            userService.register(message)
        } catch (e: Exception) {
            messageSender.send(message, message)
        }

        stateHandler.setActive(message)
        messageSender.send(message, message)
    }
}