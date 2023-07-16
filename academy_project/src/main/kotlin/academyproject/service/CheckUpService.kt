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
        cars[checkUp.carId] ?: throw CarNotFoundException()
        val id = (carCheckUps.keys.maxOrNull() ?: 0) + 1
        checkUp.id = id
        carCheckUps[id] = checkUp
        cars[checkUp.carId]?.checkUps?.add(checkUp)
        checkUpNeeded(checkUp.carId)
        return checkUp
    }

    fun getCheckUps(vin: String): Car {
        val car = cars.values.find { it.vin == vin } ?: throw CarNotFoundException()
        if (car.checkUps.isNotEmpty()) {
            car.checkUps = car.checkUps.sortedByDescending { checkUp -> checkUp.dateTime } as MutableList<CarCheckUp>
        }
        checkUpNeeded(car.carId)
        return car
    }

    fun checkUpNeeded(id: Long) {
        cars[id]?.isCheckUpNecessary =
            cars[id]?.checkUps?.none { checkUp -> checkUp.dateTime.isAfter(LocalDateTime.now().minusYears(1)) } == true
    }

    fun getManufacturers(): MutableMap<String, Int> {
        val map = mutableMapOf<String, Int>()
        for (car in cars.values) {
            map[car.manufacturer] = (map[car.manufacturer] ?: 0) + car.checkUps.size
        }
        return map
    }
}
