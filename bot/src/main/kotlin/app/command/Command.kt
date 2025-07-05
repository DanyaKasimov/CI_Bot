package app.command

interface Command {

    fun isSupported(command: String): Boolean

    fun execute(message: String)
}