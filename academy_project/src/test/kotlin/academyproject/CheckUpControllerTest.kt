package academyproject
import academyproject.entities.Car
import academyproject.service.CheckUpService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import java.time.Year

@WebMvcTest
class CheckUpControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var checkUpService: CheckUpService

    private val car = Car(1, LocalDate.now(), "BMW", "X6", Year.of(2020), "HR123456789")
    private val manufacturers: MutableMap<String, Int> = mutableMapOf<String, Int>()

    @BeforeEach
    fun setUp() {
        checkUpService = mockk()
        every { checkUpService.getCheckUps("HR123456789") } answers { car }
        every { checkUpService.getManufacturers() } answers { manufacturers }
    }

    @Test
    fun getCarDetails() {
        mockMvc.get("/car-details/HR123456789")
            .andExpect {
                status { is2xxSuccessful() }
                content { car }
            }
    }

    @Test
    fun getManufacturerDetails() {
        mockMvc.get("/manufacturers-details")
            .andExpect {
                status { is2xxSuccessful() }
                content { manufacturers }
            }
    }

    @Test
    fun getCarDetailsException() {
        mockMvc.get("/car-details/HR123456788")
            .andExpect {
                status { HttpStatus.NOT_FOUND }
                content { "Error occurred: Car not found" }
            }
    }
}
