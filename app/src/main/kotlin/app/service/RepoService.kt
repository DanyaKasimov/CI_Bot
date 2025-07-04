package app.service

import app.dto.RepoDTO
import app.entity.Repo
import app.entity.User
import app.repository.RepoRepository
import org.springframework.stereotype.Service

@Service
class RepoService(private val repoRepository: RepoRepository,
                  private val userService: UserService) {

    fun addRepo(repoDTO: RepoDTO) : Repo {
        val user: User = userService.getUser(repoDTO.chatId)

        if (repoRepository.existsByUserAndRepoLink(user, repoDTO.repoLink))
            throw RuntimeException("Вы уже подписаны на данный ресурс")

        return repoRepository.save(Repo().apply {
            this.user = user
            this.repoLink = repoDTO.repoLink
        })
    }

    fun removeRepo(repoDTO: RepoDTO) {
        val user: User = userService.getUser(repoDTO.chatId)
        val repo: Repo = repoRepository.findByUserAndRepoLink(user, repoDTO.repoLink)
            ?: throw RuntimeException("Репозиторий не найден")
        repoRepository.delete(repo)
    }

    fun getAllUsersByRepoLink(repoLink: String): List<User> {
        val repos: List<Repo> = repoRepository.findAllByRepoLink(repoLink)
        return repos.map { it.user }
    }
}