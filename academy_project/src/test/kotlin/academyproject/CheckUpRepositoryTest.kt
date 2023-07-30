package academyproject

import org.junit.jupiter.api.Test
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.springtest.MockServerTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class CheckUpRepositoryTest @Autowired constructor(
    private val mockMvc: MockMvc,
) {

    lateinit var mockServerClient: MockServerClient

    @Test
    fun carDetails() {
        mockServerClient.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/api/v1/checkup/manufacturers-details"),
        ).respond(
            HttpResponse()
                .withStatusCode(200)
                .withBody(
                    """
                        {
                            "Porsche" : 3
                        }
                    """.trimIndent(),
                ),
        )
        mockMvc.get("/api/v1/checkup/manufacturers-details")
            .andExpect {
                status { isOk() }
                content {
                    """{
                    "Porsche" : 3
                }"""
                        .trimIndent()
                }
            }
    }
}
