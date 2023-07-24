package academyproject.checkup.controller

import academyproject.car.repository.CarRepository
import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.controller.dto.CheckUpFilter
import academyproject.checkup.entity.CarCheckUp
import academyproject.checkup.service.CheckUpService
import academyproject.exception.entity.CarNotFoundException
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
    private val carRepository: CarRepository,
) {
    @GetMapping("/paged")
    private fun getAllCars(@RequestBody car: CheckUpFilter, pageable: Pageable): ResponseEntity<Page<CarCheckUp>> {
        return ResponseEntity.ok(checkUpService.getAll(car, pageable))
    }

    @PostMapping
    @ResponseBody
    fun createCar(@RequestBody checkUp: AddCheckUpDTO) =
        ResponseEntity.ok(checkUpService.addCheckUp(checkUp))

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/manufacturers-details")
    @ResponseBody
    fun getManufacturerDetails() =
        ResponseEntity.ok(checkUpService.manufacturerDetails())
}
// e73c6c80-61b9-44e8-bffd-c1d24dbb6204
// c510469c-ebef-4f08-8bac-367c9633ef34
