package academyproject.checkup.controller.dto

import academyproject.checkup.entity.CarCheckUp
import java.time.LocalDateTime
import java.util.*

class CheckUpDTO(
    var id: UUID,
    val dateTime: LocalDateTime,
    val worker: String,
    val price: Int
) {
    constructor(checkUp: CarCheckUp) : this(
        checkUp.id,
        checkUp.dateTime,
        checkUp.worker,
        checkUp.price
    )
}
