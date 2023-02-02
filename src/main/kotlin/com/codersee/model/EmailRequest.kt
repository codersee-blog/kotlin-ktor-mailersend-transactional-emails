package com.codersee.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmailRequest(
    val from: Recipient,
    val to: List<Recipient>,
    val subject: String,
    @JsonProperty("template_id") val templateId: String? = null,
    val text: String? = null,
    val html: String? = null,
    val variables: List<Variable>? = null,
    val personalization: List<CustomPersonalization>? = null,
    @JsonProperty("send_at") val sendAt: Int? = null
) {
    data class Recipient(
        val email: String,
        val name: String
    )

    data class Variable(
        val email: String,
        val substitutions: List<Substitution>
    ) {
        data class Substitution(
            @JsonProperty("var") val variable: String,
            val value: String
        )
    }

    data class CustomPersonalization(
        val email: String,
        val data: PersonalizationData
    ) {
        data class PersonalizationData(
            val name: String,
            @JsonProperty("my_super_generated_code") val code: String
        )
    }

}