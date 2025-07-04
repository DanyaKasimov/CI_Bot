package app.client

import app.config.AppConfig
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
class AppHttpClient(private val webClient: WebClient,
                    private val retry: Retry,
                    private val config: AppConfig) {

    fun get(path: String, queryParams: Map<String, String>? = null): Any? {
        val uri2 = buildUri(path, queryParams)
        return try {
            webClient.get()
                .uri(uri2)
                .retrieve()
                .toEntity(String::class.java)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
            return null
        }
    }

    fun <T, R> post(path: String, body: T, responseType: Class<R>, queryParams: Map<String, String>? = null): ResponseEntity<R>? {
        val uri = buildUri(path, queryParams)
        return try {
            webClient.post()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .toEntity(responseType)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
            return null
        }
    }

    fun post(path: String) {
        val uri = URI.create(config.app.url + path)
        try {
            webClient.post()
                .uri(uri)
                .retrieve()
                .toEntity(String::class.java)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
        }
    }

    fun delete(path: String) {
        val uri = URI.create(config.app.url + path)
        try {
            webClient.delete()
                .uri(uri)
                .retrieve()
                .toEntity(String::class.java)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
        }
    }

    fun <T, R> delete(path: String, body: T, responseType: Class<R>, queryParams: Map<String, String>? = null): ResponseEntity<R>? {
        val uri = buildUri(path, queryParams)
        return try {
            webClient.method(org.springframework.http.HttpMethod.DELETE)
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .toEntity(responseType)
                .retryWhen(retry)
                .block()
        } catch (e: WebClientResponseException) {
            return null
        }
    }

    private fun buildUri(path: String, queryParams: Map<String, String>?): URI {
        val params = queryParams ?: emptyMap()
        val multiValueMap = LinkedMultiValueMap<String, String>()
        params.forEach { (key, value) -> multiValueMap.add(key, value) }
        return UriComponentsBuilder.fromUriString(config.app.url + path)
            .queryParams(multiValueMap)
            .build()
            .toUri()
    }
}