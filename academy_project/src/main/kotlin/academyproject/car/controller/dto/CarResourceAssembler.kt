package academyproject.car.controller.dto

import academyproject.car.controller.CarController
import academyproject.car.entity.Car
import academyproject.car.entity.Model
import academyproject.checkup.controller.CarCheckUpController
import academyproject.checkup.entity.CarCheckUp
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class CarResourceAssembler : RepresentationModelAssemblerSupport<Car, CarResource>(
    CarController::class.java,
    CarResource::class.java
) {

    private val noPagination = Pageable.unpaged()
    private val nullAssembler = PagedResourcesAssembler<CarCheckUp>(null, null)

    override fun toModel(entity: Car): CarResource =
        createModelWithId(entity.carid, entity).apply {
            add(
                linkTo<CarCheckUpController> {
                    getCheckUps(sort = null, id = entity.carid, pageable = noPagination, pagedResourcesAssembler = nullAssembler)
                }.withRel("checkup")
            )
        }

    override fun instantiateModel(entity: Car): CarResource {
        return CarResource(entity.carid, entity.date, entity.model, entity.year, entity.vin)
    }
}

@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CarResource(
    val carId: UUID,
    val date: LocalDate,
    val model: Model,
    val year: Int,
    val vin: String
) : RepresentationModel<CarResource>()
