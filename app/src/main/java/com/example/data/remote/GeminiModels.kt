package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeminiRequestDto(
    val contents: List<GeminiContentDto>,
    val generationConfig: GenerationConfigDto? = null,
    val systemInstruction: GeminiContentDto? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContentDto(
    val role: String? = null,
    val parts: List<GeminiPartDto>
)

@JsonClass(generateAdapter = true)
data class GeminiPartDto(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class GenerationConfigDto(
    val temperature: Float? = 0.7f,
    val topP: Float? = 0.95f,
    val topK: Int? = 40,
    val maxOutputTokens: Int? = 4096
)

@JsonClass(generateAdapter = true)
data class GeminiResponseDto(
    val candidates: List<GeminiCandidateDto>? = null
)

@JsonClass(generateAdapter = true)
data class GeminiCandidateDto(
    val content: GeminiContentDto? = null,
    val finishReason: String? = null
)
