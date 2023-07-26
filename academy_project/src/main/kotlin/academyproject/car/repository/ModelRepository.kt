package academyproject.car.repository

import academyproject.car.entity.Model
import org.springframework.data.repository.Repository
import java.util.UUID

interface ModelRepository : Repository<Model, UUID> {

    fun findByManufacturerAndModel(manufacturer: String, model: String): Model?

    fun save(carDb: Model): Model

    fun existsByManufacturerAndModel(manufacturer: String, model: String): Boolean
}
