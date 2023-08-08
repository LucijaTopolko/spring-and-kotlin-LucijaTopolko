package academyproject.checkup.controller

import academyproject.checkup.controller.dto.CheckUpResource
import academyproject.checkup.controller.dto.CheckUpResourceAssembler
import academyproject.checkup.entity.CarCheckUp
import academyproject.checkup.service.CheckUpService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@RequestMapping("/api/v1/car/{id}/checkup")
@Controller
class CarCheckUpController(
    private val checkUpService: CheckUpService,
    private val resourceAssembler: CheckUpResourceAssembler
) {
    @GetMapping
    @ResponseBody
    fun getCheckUps(
        @PathVariable id: UUID,
        @RequestParam(required = false, defaultValue = "desc") sort: String?,
        pageable: Pageable,
        pagedResourcesAssembler: PagedResourcesAssembler<CarCheckUp>
    ): ResponseEntity<PagedModel<CheckUpResource>> =
        ResponseEntity.ok(
            pagedResourcesAssembler.toModel(
                checkUpService.getAll(sort, id, pageable),
                resourceAssembler
            )
        )
}
