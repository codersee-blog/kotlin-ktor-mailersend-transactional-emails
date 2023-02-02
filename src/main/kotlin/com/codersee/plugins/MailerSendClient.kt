package com.codersee.plugins

import com.codersee.model.EmailRequest
import com.codersee.model.ErrorResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking

fun Application.configureClient() {

    val token = environment.config.property("mailersend.token").getString()
    val baseUrl = environment.config.property("mailersend.baseUrl").getString()

    val mailerSendClient = HttpClient {
        install(ContentNegotiation) {
            jackson()
        }
    }

    runBlocking {
//        sendSimpleMessage(mailerSendClient, baseUrl, token)
//        sendMessageUsingTemplate(mailerSendClient, baseUrl, token)
    }
}

suspend fun sendSingleEmail(
    mailerSendClient: HttpClient,
    url: String,
    token: String,
    emailRequest: EmailRequest
): HttpResponse =
    mailerSendClient.post(url) {
        headers {
            append(HttpHeaders.Authorization, "Bearer $token")
        }
        contentType(ContentType.Application.Json)
        setBody(emailRequest)
    }

suspend fun handleError(response: HttpResponse) {
    val statusCode = response.status.value
    val errorBody = response.body<ErrorResponse>()

    println("Email sending failed with status code $statusCode and message '${errorBody.message}'. Errors:")

    errorBody.errors.forEach { (fieldName, fieldErrors) ->
        println("  * field name: [$fieldName]. Messages:")
        fieldErrors.forEach { error -> println("    - $error") }
    }
}

