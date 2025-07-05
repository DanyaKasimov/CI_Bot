package app.command.impl

import app.command.Command
import org.springframework.stereotype.Component

@Component
class TrackCommand : Command {
    override fun isSupported(command: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun execute(message: String) {
        TODO("Not yet implemented")
    }
}