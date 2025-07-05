package app.command.impl

import app.MessageSender
import app.command.Command
import app.constants.CommandType
import app.service.UserService
import app.state.StateHandler
import org.springframework.stereotype.Component

@Component
class DeleteCommand(private val userService: UserService,
                    private val messageSender: MessageSender,
                    private val stateHandler: StateHandler) : Command {

    override fun isSupported(command: String): Boolean {
        return CommandType.DELETE.description.equals(command, ignoreCase = true)
    }

    override fun execute(message: String) {
        try {
            userService.delete(message)
        } catch (e: Exception) {
            messageSender.send(message, e.message)
        }

        messageSender.send(message, message)
        stateHandler.deleteActive(message)
    }
}