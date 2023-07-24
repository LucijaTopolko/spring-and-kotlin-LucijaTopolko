package academyproject.checkup.repository

import academyproject.car.entity.Car
import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.controller.dto.Projection
import academyproject.checkup.entity.CarCheckUp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CheckUpRepository : JpaRepository<CarCheckUp, UUID>, JpaSpecificationExecutor<CarCheckUp> {
    fun save(checkUp: AddCheckUpDTO): CarCheckUp

    @Query("SELECT c.manufacturer AS manufacturer, COUNT(cu) AS checkUpCount FROM CarCheckUp cu JOIN cu.car c GROUP BY c.manufacturer")
    fun countByManufacturer(): List<Projection>

    fun findByCarOrderByDateTimeDesc(car: Car): List<CarCheckUp>
}
