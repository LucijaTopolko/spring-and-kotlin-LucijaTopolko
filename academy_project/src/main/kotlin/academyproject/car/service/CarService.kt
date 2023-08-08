package academyproject.car.service

import academyproject.car.controller.dto.AddCarDTO
import academyproject.car.controller.dto.CarDTO
import academyproject.car.entity.Car
import academyproject.car.repository.CarRepository
import academyproject.car.repository.ModelRepository
import academyproject.exception.entity.CarNotFoundException
import academyproject.exception.entity.WrongModelException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarService(
    private val carRepository: CarRepository,
    private val modelRepository: ModelRepository
) {

    fun addCar(car: AddCarDTO): CarDTO {
        if (!modelRepository.existsByManufacturerAndModel(car.manufacturer, car.model)) {
            throw WrongModelException()
        }
        return CarDTO.fromEntity(
            carRepository.save(
                Car(
                    date = car.date,
                    year = car.year,
                    model = modelRepository.findByManufacturerAndModel(car.manufacturer, car.model),
                    vin = car.vin
                )
            )
        )
    }

    fun getDetails(id: UUID): Car = carRepository.findById(id) ?: throw CarNotFoundException()
    fun getAll(pageable: Pageable) = carRepository.findAll(pageable)

    fun delete(id: UUID) {
        if (!carRepository.existsById(id)) throw CarNotFoundException()
        carRepository.deleteById(id)
    }
}
