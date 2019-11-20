package com.example.sample1app.db

data class SampleResponse (
    val shop: Shop?
)

data class Shop (
    val name: String?,
    val logo_image: String?
)