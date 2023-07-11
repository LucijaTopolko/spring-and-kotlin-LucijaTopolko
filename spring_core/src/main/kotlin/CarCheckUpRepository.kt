package src.main.kotlin

import java.time.LocalDateTime

interface CarCheckUpRepository {
    fun insert(performedAt: LocalDateTime, car: Car): Long
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun findAll(): Map<Long, CarCheckUp>
}
