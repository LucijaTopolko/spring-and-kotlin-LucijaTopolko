package academyproject.entities

import java.time.LocalDateTime

data class CarCheckUp(
    var id: Long = 0,
    val dateTime: LocalDateTime,
    val worker: String,
    val price: Int,
    val carId: Long,
)
