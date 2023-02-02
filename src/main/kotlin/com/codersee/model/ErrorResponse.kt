package com.codersee.model

data class ErrorResponse(
    val message: String,
    val errors: Map<String, List<String>>
)