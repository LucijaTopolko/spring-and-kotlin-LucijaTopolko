package academyproject.service

import academyproject.entities.Car
import academyproject.repository.CarRepository
import org.springframework.transaction.annotation.Transactional

class CarService(private val carRepository: CarRepository) {

    @Transactional
    fun addCar(car: Car) {
        carRepository.insertCar(car)
    }

    @Transactional
    fun getDetails(vin: String): Car {
        return carRepository.getCarDetails(vin)
    }
}
