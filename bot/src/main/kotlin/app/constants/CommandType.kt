package app.constants


enum class CommandType(val description: String) {
    START("/start"),
    TRACK("/track"),
    UNTRACK("/untrack"),
    DELETE("/delete"),
    LIST("/list"),
}