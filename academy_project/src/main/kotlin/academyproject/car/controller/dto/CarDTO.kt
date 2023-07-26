package academyproject.car.controller.dto

import academyproject.car.entity.Car
import java.time.LocalDate
import java.util.*

data class CarDTO(
    val carid: UUID,
    val date: LocalDate,
    val manufacturer: String,
    val model: String,
    var year: Int,
    val vin: String,
) {
    constructor(car: Car) : this(
        car.carid,
        car.date,
        car.manufacturer,
        car.model,
        car.year,
        car.vin,
    )
}
