package academyproject.entities

import java.time.LocalDate
import java.time.Year

data class Car(
    var carId: Long = 0,
    val date: LocalDate,
    val manufacturer: String,
    val model: String,
    var year: Year,
    val vin: String,
    var checkUps: MutableList<CarCheckUp> = mutableListOf(),
    var isCheckUpNecessary: Boolean = true,
)
