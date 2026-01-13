package dev.noctis.gpt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GptRequest(
    val prompt: String,
    @SerialName("num_return_sequences") val numReturnSequences: Int,
    @SerialName("max_length") val maxLength: Int
)