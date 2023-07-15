package academyproject.service

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.entities.CarNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CheckUpService {

    private val cars = mutableMapOf<Long, Car>()
    private val carCheckUps = mutableMapOf<Long, CarCheckUp>()

    fun addCar(car: Car): Car {
        val id = (cars.keys.maxOrNull() ?: 0) + 1
        car.carId = id
        cars[id] = car
        return car
    }

    fun addCheckUp(checkUp: CarCheckUp): CarCheckUp {
        val car = cars[checkUp.carId] ?: throw CarNotFoundException()
        val id = (carCheckUps.keys.maxOrNull() ?: 0) + 1
        checkUp.id = id
        carCheckUps[id] = checkUp
        cars[checkUp.carId]?.checkUps?.add(checkUp)
        return checkUp
    }

    fun getCheckUps(vin: String): List<CarCheckUp> {
        val car = cars.values.find { it.vin == vin } ?: throw CarNotFoundException()
        val checkUps: List<CarCheckUp>? = car.checkUps?.sortedByDescending { checkUp -> checkUp.dateTime }
        if (checkUps != null) {
            if (checkUps.isEmpty()) {
                println("Car with ID ${car.carId} needs check up!")
            } else {
                checkUpNeeded(checkUps, car.carId)
                return checkUps
            }
        }
        return emptyList()
    }

    fun checkUpNeeded(checkUps: List<CarCheckUp>, id: Long) {
        if (checkUps.first().dateTime.isBefore(LocalDateTime.now().minusYears(1))) {
            println("Car with ID $id needs check up!")
        }
    }

    fun getManufacturers(): MutableMap<String, Int> {
        val map = mutableMapOf<String, Int>()
        for (car in cars.values) {
            map[car.manufacturer] = (map[car.manufacturer] ?: 0) + car.checkUps.size
        }
        return map
    }
}
