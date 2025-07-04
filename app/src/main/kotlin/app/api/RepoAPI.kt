package app.api

import app.entity.Repo
import jakarta.validation.Valid
import app.dto.RepoDTO
import app.entity.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/repos")
@Tag(name = "Repo API", description = "Управление репозиториями пользователей")
interface RepoAPI {

    @PostMapping
    @Operation(summary = "Добавить репозиторий", description = "Подписывает пользователя на репозиторий")
    fun addRepo(@RequestBody @Valid repoDTO: RepoDTO): Repo

    @DeleteMapping
    @Operation(summary = "Удалить репозиторий", description = "Удаляет репозиторий у пользователя")
    fun removeRepo(@RequestBody @Valid repoDTO: RepoDTO)

    @GetMapping("/users/{repoLink}")
    @Operation(summary = "Пользователи по репозиторию",
        description = "Получить всех пользователей, подписанных на репозиторий")
    fun getUsersByRepoLink(@PathVariable("repoLink") @Valid repoLink: String): List<User>
}