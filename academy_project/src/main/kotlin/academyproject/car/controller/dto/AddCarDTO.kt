package academyproject.car.controller.dto

import academyproject.car.entity.Car
import java.time.LocalDate
import java.util.*

data class AddCarDTO(
    val date: LocalDate,
    val manufacturer: String,
    val model: String,
    var year: Int,
    val vin: String,
) {
    fun car() = Car(
        date = date,
        manufacturer = manufacturer,
        model = model,
        year = year,
        vin = vin,
    )
}
