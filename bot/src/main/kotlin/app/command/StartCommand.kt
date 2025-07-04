package app.command

import app.constants.CommandType
import org.springframework.stereotype.Component

@Component
class StartCommand : Command {

    override fun isSupported(command: String): Boolean {
        return CommandType.START.description.equals(command, ignoreCase = true)
    }

    override fun execute() {

    }
}