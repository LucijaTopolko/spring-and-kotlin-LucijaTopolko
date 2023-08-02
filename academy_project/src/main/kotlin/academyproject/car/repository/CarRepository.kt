package academyproject.car.repository

import academyproject.car.entity.Car
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

interface CarRepository : Repository<Car, UUID> {
    fun save(car: Car): Car
    fun findAll(pageable: Pageable): Page<Car>
    fun findById(carId: UUID): Car?
    fun existsById(carId: UUID): Boolean
    fun deleteById(carId: UUID): Car?
    fun findByVin(vin: String): Car?
    fun deleteAll()
}
