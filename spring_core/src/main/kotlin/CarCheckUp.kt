package src.main.kotlin

import java.time.LocalDateTime

data class CarCheckUp(val id: Long, val performedAt: LocalDateTime, val car: Car)
