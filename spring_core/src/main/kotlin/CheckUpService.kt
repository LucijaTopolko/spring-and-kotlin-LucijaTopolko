package src.main.kotlin

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CheckUpService(
    /*@Qualifier("inMemoryCarCheckUpRepository")*/
    private val carCheckUpRepository: CarCheckUpRepository,
) {
    fun insert(performedAt: LocalDateTime, car: Car): Long {
        return carCheckUpRepository.insert(performedAt, car)
    }

    fun findById(id: Long): CarCheckUp {
        return carCheckUpRepository.findById(id)
    }

    fun deleteById(id: Long): CarCheckUp {
        return carCheckUpRepository.deleteById(id)
    }

    fun findAll(): Map<Long, CarCheckUp> {
        return carCheckUpRepository.findAll()
    }
}
