package com.codersee.plugins

import com.codersee.model.EmailRequest
import io.ktor.client.*
import io.ktor.http.*
import java.util.*


suspend fun sendMessageUsingTemplate(
    mailerSendClient: HttpClient,
    baseUrl: String,
    token: String
) {
    val subject = "Please Reset Your Password"

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
        templateId = "v69oxl587pzl785k",
        personalization = listOf(
            EmailRequest.CustomPersonalization(
                email = "example@test.codersee.com",
                data = EmailRequest.CustomPersonalization.PersonalizationData(
                    name = "Pjoter",
                    code = UUID.randomUUID().toString()
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
