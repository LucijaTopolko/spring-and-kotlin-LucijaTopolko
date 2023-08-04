package academyproject

import academyproject.car.entity.Car
import academyproject.car.entity.Model
import academyproject.car.repository.CarRepository
import academyproject.car.repository.ModelRepository
import academyproject.checkup.entity.CarCheckUp
import academyproject.checkup.repository.CheckUpRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientAppApplicationTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val modelRepository: ModelRepository,
    private val carRepository: CarRepository,
    private val checkUpRepository: CheckUpRepository,
) {

    private lateinit var carId: UUID
    private lateinit var id: UUID

    @BeforeAll
    fun setup() {
        modelRepository.save(carDb = Model(manufacturer = "Porsche", model = "Panamera"))
        val model: Model = modelRepository.findByManufacturerAndModel("Porsche", "Panamera")
        val car = Car(carid = UUID.fromString("f49ecbaf-ce69-476f-87e1-65866e26044f"), date = LocalDate.now(), model, 2020, "test")
        val saved = carRepository.save(car)
        carId = saved.carid
        val checkup = CarCheckUp(id = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), dateTime = LocalDateTime.now(), worker = "James", price = 200, car = saved)
        val saved1 = checkUpRepository.save(checkup)
        id = saved1.id
    }

    @Test
    fun contextLoads() {}

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `admin can access all cars`() {
        mockMvc.get("/api/v1/car/paged")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `user can not access all cars`() {
        mockMvc.get("/api/v1/car/paged")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `admin can see car details`() {
        mockMvc.get("/api/v1/car/$carId")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `user can see car details`() {
        mockMvc.get("/api/v1/car/$carId")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `admin can see manufacturer details`() {
        mockMvc.get("/api/v1/checkup/manufacturers-details")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `user can see manufacturer details`() {
        mockMvc.get("/api/v1/checkup/manufacturers-details")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `user can't create checkup`() {
        mockMvc.post("/api/v1/checkup")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `user can't delete checkup`() {
        mockMvc.delete("/api/v1/checkup/delete/$id")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `admin can delete checkup`() {
        mockMvc.delete("/api/v1/checkup/delete/$id")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `user can't delete car`() {
        mockMvc.delete("/api/v1/car/delete/$carId")
            .andExpect {
                status { isForbidden() }
            }
            .andReturn().response.contentAsString
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `admin can delete car`() {
        mockMvc.delete("/api/v1/car/delete/$carId")
            .andExpect {
                status { is2xxSuccessful() }
            }
            .andReturn().response.contentAsString
    }
}
