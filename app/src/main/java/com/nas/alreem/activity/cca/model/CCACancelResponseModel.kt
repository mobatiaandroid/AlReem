package com.nas.alreem.activity.cca.model

data class CCACancelResponseModel(
    val `data`: Data?,
    val message: String?,
    val status: Int?,
    val validation_errors: List<Any?>?
) {
    class Data
}