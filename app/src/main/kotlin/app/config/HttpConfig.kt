package app.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import reactor.util.retry.Retry
import reactor.util.retry.Retry.RetrySignal
import reactor.util.retry.RetryBackoffSpec
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
@Component
class HttpConfig(private val appConfig: AppConfig) {

    @Bean
    fun webClientRetry(): Retry {
        return Retry
            .fixedDelay(appConfig.http.retry.maxAttempts,
                Duration.ofMillis(appConfig.http.retry.backoffMillis))
            .filter { throwable: Throwable ->
                if (throwable is WebClientResponseException) {
                    return@filter appConfig.http.retry.statusCodes.contains(throwable.statusCode)
                }
                false
            }
            .onRetryExhaustedThrow { _: RetryBackoffSpec?, signal: RetrySignal ->
                RuntimeException(
                    "Повторные попытки исчерпаны: " + signal.failure().localizedMessage
                )
            }
    }

    @Bean
    fun webClient(): WebClient {
        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, appConfig.http.timeout.connect.toMillis().toInt())
            .responseTimeout(appConfig.http.timeout.response)
            .doOnConnected { conn: Connection ->
                conn.addHandlerLast(
                    ReadTimeoutHandler(
                        appConfig.http.timeout.response.toMillis(), TimeUnit.MILLISECONDS
                    )
                )
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

}