package app.client

import app.config.AppConfig
import app.dto.MessageDTO
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.util.UriComponentsBuilder
import reactor.util.retry.Retry
import java.net.URI

@Component
class HttpClient(private val client: WebClient,
                 private val retry: Retry,
                 private val config: AppConfig) {

    fun <T> getApiAccess(path: String,
                         responseType: ParameterizedTypeReference<T>,
                         queryParams: Map<String, String>,
                         headers: Map<String, String>): ResponseEntity<T>? {
        val uri = buildUri(path, queryParams)

        return try {
            client.get()
                .uri(uri)
                .headers { h -> headers.forEach { (k, v) -> h[k] = v } }
                .retrieve()
                .toEntity(responseType)
                .retryWhen(retry)
                .block()!!
        } catch (e : WebClientResponseException) {
            return null
        }
    }

    fun sendBotNotification(messageDTO: MessageDTO): ResponseEntity<MessageDTO>? {
        return try {
            client.post()
                .uri("${config.bot.url}/notifications")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(messageDTO)
                .retrieve()
                .toEntity(MessageDTO::class.java)
                .retryWhen(retry)
                .block()!!
        } catch (e: WebClientResponseException) {
            return null;
        }
    }

    private fun buildUri(path: String, queryParams: Map<String, String>): URI {
        val params = LinkedMultiValueMap<String, String>().apply {
            queryParams.forEach { (k, v) -> add(k, v) }
        }

        return UriComponentsBuilder
            .fromUriString(path)
            .queryParams(params)
            .build()
            .toUri()
    }
}