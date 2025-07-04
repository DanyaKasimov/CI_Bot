package app.config

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
data class AppConfig(val telegram: @Valid TelegramProperties,
                     val kafka: @Valid KafkaProperties,
                     val app: @Valid AppProperties,
                     val http: @Valid HttpProperties
) {
    data class TelegramProperties(val token: @NotEmpty String?)

    data class KafkaProperties(val bootstrapServers: @NotEmpty String,
                               val group: @NotEmpty String,
                               val topics: @Valid Topics
    )

    data class Topics(val notification: @NotEmpty String)

    data class AppProperties(val url: @NotEmpty String)

    data class HttpProperties(val timeout: @Valid Timeout,
                              val retry: @Valid Retry,
    )

    data class Retry(val maxAttempts: @Min(1) Long,
                     val backoffMillis: @Min(0) Long,
                     val statusCodes: @NotEmpty MutableList<HttpStatus>
    )

    data class Timeout(val connect: @NotNull Duration,
                       val response: @NotNull Duration)
}