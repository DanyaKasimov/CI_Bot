package app.client

import app.config.AppConfig
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
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
class AppHttpClient(private val webClient: WebClient,
                    private val retry: Retry,
                    private val config: AppConfig) {

    fun <T : Any?, R> post(path: String,
                           responseType: Class<R>,
                           body: T? = null,
                           queryParams: Map<String, String> = emptyMap()): ResponseEntity<R>? {
        val uri = buildUri(path, queryParams)
        return try {
            val request = webClient
                .post()
                .uri(uri)

            if (body != null) {
                request
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
            }

            request
                .retrieve()
                .toEntity(responseType)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
            null
        }
    }

    fun <T : Any?> delete(path: String,
                          body: T? = null,
                          queryParams: Map<String, String> = emptyMap()) {
        val uri = buildUri(path, queryParams)
        try {
            val request = webClient
                .method(HttpMethod.DELETE)
                .uri(uri)

            if (body != null) {
                request
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
            }

            request
                .retrieve()
                .toBodilessEntity()
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {

        }
    }

    fun <R> get(path: String,
                responseType: ParameterizedTypeReference<R>,
                queryParams: Map<String, String> = emptyMap()): ResponseEntity<R>? {
        val uri = buildUri(path, queryParams)
        return try {
            webClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(responseType)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
            null
        }
    }

    private fun buildUri(path: String, params: Map<String, String> = emptyMap()): URI {
        val multiValueMap = LinkedMultiValueMap<String, String>()
        params.forEach { (key, value) -> multiValueMap.add(key, value) }
        return UriComponentsBuilder.fromUriString(config.app.url + path)
            .queryParams(multiValueMap)
            .build()
            .toUri()
    }
}