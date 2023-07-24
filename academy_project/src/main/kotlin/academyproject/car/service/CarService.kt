package academyproject.car.service

import academyproject.car.controller.dto.AddCarDTO
import academyproject.car.controller.dto.CarDTO
import academyproject.car.controller.dto.CarPrintDTO
import academyproject.car.repository.CarRepository
import academyproject.checkup.repository.CheckUpRepository
import academyproject.exception.entity.CarNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CarService(
    private val carRepository: CarRepository,
    private val checkUpRepository: CheckUpRepository,
) {

    fun addCar(car: AddCarDTO) {
        CarDTO(
            carRepository.save(
                car.car(),
            ),
        )
    }

    fun getDetails(vin: String): CarPrintDTO {
        return carRepository.findByVin(vin)?.let {
            val checkUps = checkUpRepository.findByCarOrderByDateTimeDesc(it)
            val needed: Boolean = checkUps.firstOrNull()?.dateTime == null || checkUps.first().dateTime.isBefore(LocalDateTime.now().minusYears(1))
            CarPrintDTO(it, checkUps, needed)
        } ?: throw CarNotFoundException()
    }

    fun getAll(pageable: Pageable): Page<CarDTO> =
        carRepository.findAll(pageable).map { CarDTO(it) }
}
