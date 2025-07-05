package app.repository

import app.entity.Repo
import app.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RepoRepository : JpaRepository<Repo, UUID> {

    fun existsByUserAndRepoLink(user: User, repoLink: String): Boolean

    fun findByUserAndRepoLink(user: User, repoLink: String): Repo?

    fun findAllByRepoLink(repoLink: String): List<Repo>

    fun deleteAllByUser(user: User)

    fun findAllByUser(user: User): List<Repo>
}