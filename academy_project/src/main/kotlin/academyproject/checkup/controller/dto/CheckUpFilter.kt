package academyproject.checkup.controller.dto

import academyproject.car.entity.Car
import java.time.LocalDateTime

data class CheckUpFilter(
    val dateTime: LocalDateTime? = null,
    val worker: String? = null,
    val price: Int? = null,
    var car: Car? = null,
)
