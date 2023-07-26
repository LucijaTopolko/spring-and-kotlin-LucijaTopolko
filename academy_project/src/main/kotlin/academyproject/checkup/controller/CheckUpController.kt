package academyproject.checkup.controller

import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.controller.dto.CheckUpFilter
import academyproject.checkup.entity.CarCheckUp
import academyproject.checkup.service.CheckUpService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports

@RequestMapping("/checkup")
@Controller
class CheckUpController(
    private val checkUpService: CheckUpService,
) {
    @GetMapping("/paged")
    private fun getAllCars(@RequestBody car: CheckUpFilter, pageable: Pageable): ResponseEntity<Page<CarCheckUp>> {
        return ResponseEntity.ok(checkUpService.getAll(car, pageable))
    }

    @PostMapping
    @ResponseBody
    fun createCar(@RequestBody checkUp: AddCheckUpDTO) =
        ResponseEntity.ok(checkUpService.addCheckUp(checkUp))

    @ExceptionHandler(value = [(RuntimeException::class)])
    fun handleException(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/manufacturers-details")
    @ResponseBody
    fun getManufacturerDetails() =
        ResponseEntity.ok(checkUpService.manufacturerDetails())
}
