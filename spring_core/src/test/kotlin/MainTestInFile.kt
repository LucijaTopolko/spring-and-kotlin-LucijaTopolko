package src.test.kotlin

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import src.main.kotlin.* // ktlint-disable no-wildcard-imports
import java.time.LocalDateTime

class MainTestInFile {
    private lateinit var carCheckUpService: CheckUpService
    private val checkUpRepository: CarCheckUpRepository = mockk<InFileCarCheckUpRepository>()

    @BeforeEach
    fun setUp() {
        carCheckUpService = CheckUpService(checkUpRepository)
    }

    @Test
    fun check_findAll() {
        every { checkUpRepository.findAll() }.returns(
            mapOf(
                1L to CarCheckUp(1, LocalDateTime.of(2021, 8, 14, 15, 20), Car("Porsche", "Panamera", "WP0AB2A73EL050316")),
                2L to CarCheckUp(2, LocalDateTime.of(2022, 11, 14, 12, 27), Car("BMW", "X6", "WP0JB0932FS859246")),
            ),
        )

        val checkUps = carCheckUpService.findAll()
        assertThat(checkUps).isEqualTo(
            mapOf(
                1L to CarCheckUp(1, LocalDateTime.of(2021, 8, 14, 15, 20), Car("Porsche", "Panamera", "WP0AB2A73EL050316")),
                2L to CarCheckUp(2, LocalDateTime.of(2022, 11, 14, 12, 27), Car("BMW", "X6", "WP0JB0932FS859246")),
            ),
        )

        verify(exactly = 1) { checkUpRepository.findAll() }
    }

    @Test
    fun check_findById() {
        val id = 1L
        val performadAt = LocalDateTime.now()
        every { checkUpRepository.findById(any()) } returns CarCheckUp(id, performadAt, Car("BMW", "X6", "WP0JB0932FS859246"))

        val car = carCheckUpService.findById(id)
        assertThat(car).isEqualTo(CarCheckUp(id, performadAt, Car("BMW", "X6", "WP0JB0932FS859246")))
        verify(exactly = 1) { checkUpRepository.findById(id) }
    }

    @Test
    fun check_findById_exception() {
        val id = 1L
        every { checkUpRepository.findById(any()) } throws CarCheckUpNotFoundException(id)

        assertThatThrownBy { carCheckUpService.findById(id) }
            .isInstanceOf(CarCheckUpNotFoundException::class.java)
            .hasMessage("Car check-up ID $id not found")

        verify(exactly = 1) { checkUpRepository.findById(id) }
    }

    @Test
    fun check_deleteById() {
        val id = 1L
        val performedAt = LocalDateTime.now()
        every { checkUpRepository.deleteById(any()) } returns CarCheckUp(id, performedAt, Car("BMW", "X6", "WP0JB0932FS859246"))

        val checkUp = carCheckUpService.deleteById(id)
        assertThat(checkUp).isEqualTo(CarCheckUp(id, performedAt, Car("BMW", "X6", "WP0JB0932FS859246")))
        verify(exactly = 1) { checkUpRepository.deleteById(id) }
    }

    @Test
    fun check_deleteById_exception() {
        val id = 1L
        every { checkUpRepository.deleteById(any()) } throws CarCheckUpNotFoundException(id)

        assertThatThrownBy { carCheckUpService.deleteById(id) }
            .isInstanceOf(CarCheckUpNotFoundException::class.java)
            .hasMessage("Car check-up ID $id not found")

        verify(exactly = 1) { checkUpRepository.deleteById(id) }
    }

    @Test
    fun check_insert() {
        val id = 1L
        val performedAt = LocalDateTime.now()
        every { checkUpRepository.insert(any(), any()) } returns id

        assertThat(carCheckUpService.insert(performedAt, Car("BMW", "X6", "WP0JB0932FS859246"))).isEqualTo(id)

        verify(exactly = 1) { checkUpRepository.insert(performedAt, Car("BMW", "X6", "WP0JB0932FS859246")) }
    }
}
