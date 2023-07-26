package academyproject.checkup.controller.dto

import academyproject.car.entity.Car
import academyproject.checkup.entity.CarCheckUp
import java.time.LocalDateTime
import java.util.*

data class AddCheckUpDTO(
    val dateTime: LocalDateTime,
    val worker: String,
    val price: Int,
    val carid: UUID,
) {
    fun checkup(carFetch: (UUID) -> Car): CarCheckUp {
        return CarCheckUp(
            dateTime = dateTime,
            worker = worker,
            price = price,
            car = carFetch.invoke(carid),
        )
    }
}
