package com.codersee.plugins

import com.codersee.model.EmailRequest
import io.ktor.client.*
import io.ktor.http.*

suspend fun sendSimpleMessage(
    mailerSendClient: HttpClient,
    baseUrl: String,
    token: String
) {
    val subject = "Hello from {\$company}!"
    val html = """
        Hello <b>{${'$'}name}</b>, nice to meet you!
    """.trimIndent()

    val emailRequest = EmailRequest(
        from = EmailRequest.Recipient(
            email = "example@test.codersee.com",
            name = "Codersee"
        ),
        to = listOf(
            EmailRequest.Recipient(
                email = "example@test.codersee.com",
                name = "Pjoter"
            )
        ),
        subject = subject,
        html = html,
        variables = listOf(
            EmailRequest.Variable(
                email = "example@test.codersee.com",
                substitutions = listOf(
                    EmailRequest.Variable.Substitution(
                        variable = "company",
                        value = "Codersee"
                    ),
                    EmailRequest.Variable.Substitution(
                        variable = "name",
                        value = "Piotr"
                    )
                )
            )
        )
    )

    val response = sendSingleEmail(
        mailerSendClient = mailerSendClient,
        url = "$baseUrl/email",
        token = token,
        emailRequest = emailRequest
    )

    if (response.status == HttpStatusCode.Accepted)
        println("Email sent successfully.")
    else
        handleError(response)

}