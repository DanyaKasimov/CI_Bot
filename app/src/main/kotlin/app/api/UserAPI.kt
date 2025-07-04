package app.api

import app.entity.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
interface UserAPI {

    @PostMapping("/{chatId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить пользователя", description = "Создает нового пользователя по chatId")
    fun addUser(@PathVariable @NotBlank @Valid chatId: String): User

    @DeleteMapping("/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя и его репозитории")
    fun deleteUser(@PathVariable @NotBlank @Valid chatId: String)
}