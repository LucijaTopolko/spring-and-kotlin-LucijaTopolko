package academyproject.checkup.repository

import academyproject.car.entity.Car
import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.controller.dto.Projection
import academyproject.checkup.entity.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import java.util.*

interface CheckUpRepository : Repository<CarCheckUp, UUID> {
    fun save(checkUp: CarCheckUp): CarCheckUp

    @Query("SELECT m.manufacturer AS manufacturer, COUNT(cu) AS count FROM CarCheckUp cu JOIN cu.car c JOIN c.model m GROUP BY m.manufacturer")
    fun countByManufacturer(): List<Projection>

    fun findByCarOrderByDateTimeDesc(car: Car, pageable: Pageable): Page<CarCheckUp>
    fun findByCarOrderByDateTimeAsc(car: Car, pageable: Pageable): Page<CarCheckUp>
}
