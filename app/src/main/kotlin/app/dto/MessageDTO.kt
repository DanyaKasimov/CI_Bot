package app.dto

data class MessageDTO(
    var repo: String,
    var workflow: String,
    var status: String,
    var conclusion: String,
    var htmlUrl: String,
) {

    override fun toString(): String {
        return """
                ✅ CI/CD завершён
                📦 Репозиторий: $repo
                🔧 Workflow: $workflow
                🟡 Статус: $status
                🟢 Результат: $conclusion
                🔗 $htmlUrl
            """.trimIndent()
    }
}