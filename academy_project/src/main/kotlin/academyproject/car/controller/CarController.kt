package academyproject.car.controller

import academyproject.car.controller.dto.AddCarDTO
import academyproject.car.service.CarService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports
import java.util.*

@RequestMapping("/car")
@Controller
class CarController(private val carService: CarService) {

    @GetMapping("/paged")
    private fun getAllCars(pageable: Pageable) =
        ResponseEntity.ok(carService.getAll(pageable))

    @PostMapping
    @ResponseBody
    fun createCar(@RequestBody car: AddCarDTO) =
        ResponseEntity.ok(carService.addCar(car))

    @GetMapping("/{id}")
    @ResponseBody
    fun getCarDetails(@PathVariable id: UUID) =
        ResponseEntity.ok(carService.getDetails(id))

    @ExceptionHandler(value = [(RuntimeException::class)])
    fun handleException(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }
}
