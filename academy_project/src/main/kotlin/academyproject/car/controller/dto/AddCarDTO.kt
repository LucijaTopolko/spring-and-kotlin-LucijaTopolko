package academyproject.car.controller.dto

import academyproject.car.entity.Car
import academyproject.car.repository.ModelRepository
import academyproject.exception.entity.WrongModelException
import java.time.LocalDate
import java.util.*

data class AddCarDTO(
    val date: LocalDate,
    val manufacturer: String,
    val model: String,
    var year: Int,
    val vin: String,
) {
    fun car(
        modelRepository: ModelRepository,
    ): Car {
        val newModel = modelRepository.findByManufacturerAndModel(manufacturer, model) ?: throw WrongModelException()
        return Car(
            date = date,
            model = newModel,
            year = year,
            vin = vin,
        )
    }
}
