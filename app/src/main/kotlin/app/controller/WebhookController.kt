package app.controller

import org.springframework.web.bind.annotation.*
import com.fasterxml.jackson.databind.JsonNode

@RestController
class WebhookController() {

    @PostMapping("/github-webhook")
    fun handleWebhook(@RequestBody payload: JsonNode): String {
        println("payload: $payload")

        return "ok"
    }
}