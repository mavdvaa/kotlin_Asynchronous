import kotlinx.coroutines.*
import java.net.URL
import java.net.HttpURLConnection

fun main() = runBlocking {
    var result: String = ""
    val urls = listOf("https://www.google.com", "https://www.facebook.com",
    "https://www.github.com",
    "https://www.twitter.com",
    "https://www.instagram.com",
        "https://etu.ru/",
        "https://vec.etu.ru/moodle/",
        "https://norwaypark-spb.ru/",
        "https://www.mirage.ru/spb/schedule/",
        "https://spb.kassir.ru/"
    )
    val coroutines = urls.map { url ->
        async { url to checkWebsite(url) }
    }
    coroutines.forEach {
        val(url, check) = it.await()
        if (check == true) {
            result = "доступен"
        } else {
            result = "недоступен"
        }
        println("Сайт ${url} ${result}")
    }
}

suspend fun checkWebsite(url: String): Boolean {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.connect()
        connection.responseCode == HttpURLConnection.HTTP_OK
    } catch (e: Exception) {
        false
    }
}
