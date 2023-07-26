package academyproject.car.repository

import academyproject.car.entity.Car
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

interface CarRepository : Repository<Car, UUID> {
    fun save(car: Car): Car
    fun findByVin(vin: String): Car?
    fun findAll(pageable: Pageable): Page<Car>

    fun findById(id: UUID): Car?
    fun deleteAll()
}
