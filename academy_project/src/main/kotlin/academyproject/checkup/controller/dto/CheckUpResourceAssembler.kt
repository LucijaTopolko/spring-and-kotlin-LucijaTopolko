package academyproject.checkup.controller.dto

import academyproject.car.controller.CarController
import academyproject.car.entity.Car
import academyproject.checkup.controller.CheckUpController
import academyproject.checkup.entity.CarCheckUp
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class CheckUpResourceAssembler : RepresentationModelAssemblerSupport<CarCheckUp, CheckUpResource>(
    CheckUpController::class.java,
    CheckUpResource::class.java,
) {
    override fun toModel(entity: CarCheckUp): CheckUpResource =
        CheckUpResource(
            id = entity.id,
            dateTime = entity.dateTime,
            worker = entity.worker,
            price = entity.price,
            car = entity.car,
        ).apply {
            add(
                linkTo<CarController> {
                    getCarDetails(entity.id)
                }.withRel("car"),
            )
        }
}

@Relation(collectionRelation = IanaLinkRelations.ITEM_VALUE)
data class CheckUpResource(
    val id: UUID,
    val dateTime: LocalDateTime,
    val worker: String,
    val price: Int,
    val car: Car,
) : RepresentationModel<CheckUpResource>()
