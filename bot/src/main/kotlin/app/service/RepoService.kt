package app.service

import app.client.AppHttpClient
import app.dto.Repo
import app.dto.RepoDTO
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class RepoService(private val appHttpClient: AppHttpClient) {

    companion object {
        private const val REPO_ENDPOINT = "/api/repos"
    }

    fun add(repoDTO: RepoDTO): Repo? {
        return appHttpClient.post(
            REPO_ENDPOINT,
            Repo::class.java,
            repoDTO)
            ?.body
    }

    fun remove(repoDTO: RepoDTO) {
        appHttpClient.delete(REPO_ENDPOINT, repoDTO)
    }

    fun getAllReposByUser(chatId: Long): List<Repo>? {
        val uri = "$REPO_ENDPOINT/users/$chatId"
        return appHttpClient
            .get(uri, object : ParameterizedTypeReference<List<Repo>>() {})
            ?.body
    }
}