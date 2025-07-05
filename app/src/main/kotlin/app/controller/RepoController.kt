package app.controller

import app.api.RepoAPI
import app.dto.RepoDTO
import app.entity.Repo
import app.entity.User
import app.service.RepoService
import org.springframework.web.bind.annotation.RestController

@RestController
class RepoController(private val repoService: RepoService) : RepoAPI {

    override fun addRepo(repoDTO: RepoDTO): Repo = repoService.addRepo(repoDTO)

    override fun removeRepo(repoDTO: RepoDTO) = repoService.removeRepo(repoDTO)

    override fun getReposByUser(chatId: String): List<Repo> = repoService.getListRepos(chatId)
}