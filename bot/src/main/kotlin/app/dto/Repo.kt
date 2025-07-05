package app.dto

import java.util.UUID

data class Repo(val id: UUID, val chatId: String, val repoLink: String) {
}