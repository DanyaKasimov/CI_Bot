package app.config

import app.constants.TransportEnum
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import java.time.Duration

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
data class AppConfig (val github: @Valid GitHubProperties,
                      val http: @Valid HttpProperties,
                      val kafka: @Valid KafkaProperties,
                      val bot: @Valid BotProperties,
                      val transport: @Valid TransportProperties,) {

    data class GitHubProperties(val apiUrl: @NotEmpty String,
                                val baseUrl: @NotEmpty String,
                                val token: @NotEmpty String)

    data class HttpProperties(val retry: @Valid Retry,
                              val timeout: @Valid Timeout)

    data class Retry(val maxAttempts: @Min(1) Long,
                     val backoffMillis: @Min(0) Long,
                     val statusCodes: @NotEmpty MutableList<HttpStatus>
    )

    data class Timeout(val connect: @NotNull Duration,
                       val response: @NotNull Duration
    )

    data class KafkaProperties(val bootstrapServers: @NotEmpty String,
                               val topics: @Valid Topics)

    data class Topics(val notification: @NotEmpty String)

    data class BotProperties(val url: @NotEmpty String)

    data class TransportProperties(@NotEmpty val type: TransportEnum)

}