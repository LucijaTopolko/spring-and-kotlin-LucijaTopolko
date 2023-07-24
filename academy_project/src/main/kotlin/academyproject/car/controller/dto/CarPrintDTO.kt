package academyproject.car.controller.dto

import academyproject.car.entity.Car
import academyproject.checkup.controller.dto.CheckUpDTO
import academyproject.checkup.entity.CarCheckUp
import java.time.LocalDate

class CarPrintDTO(
    val date: LocalDate,
    val manufacturer: String,
    val model: String,
    var year: Int,
    val vin: String,
    val checkUps: List<CheckUpDTO>,
    val ischeckupnecessary: Boolean,
) {
    constructor(car: Car, checkUps: List<CarCheckUp>, ischeckupnecessary: Boolean) : this(
        car.date,
        car.manufacturer,
        car.model,
        car.year,
        car.vin,
        checkUps.map { CheckUpDTO(it) },
        ischeckupnecessary,
    )
}
