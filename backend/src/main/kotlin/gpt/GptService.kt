package dev.noctis.gpt

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.random.nextInt

class      GptService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun generate(prompt: String): String {
        println("GENERATING")
        val url = "http://model:8000/generate"

        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(GptRequest(
                prompt = prompt,
                numReturnSequences = 1,
                maxLength = Random.nextInt(25..75)
            ))
        }

        val data = response.body<GptResponse>()

        return data.generatedTexts[0]
    }
}
