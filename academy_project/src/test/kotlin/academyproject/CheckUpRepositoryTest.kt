package academyproject

import academyproject.car.entity.Car
import academyproject.car.repository.CarRepository
import academyproject.checkup.controller.dto.CheckUpFilter
import academyproject.checkup.repository.CheckUpRepository
import academyproject.checkup.repository.CheckUpSpecifications
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CheckUpRepositoryTest @Autowired constructor(
    val carRepository: CarRepository,
    val checkUpRepository: CheckUpRepository,
) {

    @BeforeEach
    fun setup() {
        carRepository.deleteAll()
        checkUpRepository.deleteAll()

        var car1 = Car(
            date = LocalDate.now(),
            manufacturer = "BMW",
            model = "X6",
            vin = "HRabc",
            year = 2023,
        )
        var car2 = Car(
            date = LocalDate.now(),
            manufacturer = "Porsche",
            model = "Panamera",
            vin = "HRasdf",
            year = 2021,
        )
        var car3 = Car(
            date = LocalDate.now(),
            manufacturer = "BMW",
            model = "X3",
            vin = "HRtzu",
            year = 2016,
        )
        carRepository.save(car1)
        carRepository.save(car2)
        carRepository.save(car3)
    }

    @Test
    fun findAllCars() {
        val pageable = PageRequest.of(0, 2)
        val all = carRepository.findAll(pageable)
        assertThat(all.totalPages).isEqualTo(2)
    }

    @Test
    fun findAllCheckUps() {
        val car1 = Car(
            date = LocalDate.now(),
            manufacturer = "BMW",
            model = "X6",
            vin = "HR123",
            year = 2023,
        )
        val pageable = PageRequest.of(0, 2)
        val filter = CheckUpFilter(car = car1)
        val spec = CheckUpSpecifications.toSpecification(filter)
        val all = checkUpRepository.findAll(spec, pageable)
        assertThat(all.totalElements).isEqualTo(0)
    }

    @Test
    fun getCarDetails() {
        val details = carRepository.findByVin("HRabc")
        assertThat(details?.manufacturer).isEqualTo("BMW")
    }
}
