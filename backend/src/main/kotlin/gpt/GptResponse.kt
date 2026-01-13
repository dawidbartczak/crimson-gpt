package dev.noctis.gpt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GptResponse(
    @SerialName("generated_texts") val generatedTexts: List<String>
)
