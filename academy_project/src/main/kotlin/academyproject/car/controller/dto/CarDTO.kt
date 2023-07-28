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

    companion object {
        fun fromEntity(car: Car) = CarDTO(
            carid = car.carid,
            date = car.date,
            manufacturer = car.model.manufacturer,
            model = car.model.model,
            year = car.year,
            vin = car.vin,
        )
    }
}
