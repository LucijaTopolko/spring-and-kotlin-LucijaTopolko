package academyproject.car.controller

import academyproject.car.controller.dto.AddCarDTO
import academyproject.car.controller.dto.CarResource
import academyproject.car.controller.dto.CarResourceAssembler
import academyproject.car.entity.Car
import academyproject.car.service.CarService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RequestMapping("/api/v1/car")
@Controller
class CarController(
    private val carService: CarService,
    private val resourceAssembler: CarResourceAssembler
) {

    @GetMapping("/paged")
    private fun getAllCars(pageable: Pageable, pagedResourceAssembler: PagedResourcesAssembler<Car>): ResponseEntity<PagedModel<CarResource>> {
        return ResponseEntity.ok(
            pagedResourceAssembler.toModel(
                carService.getAll(pageable),
                resourceAssembler
            )
        )
    }

    @PostMapping
    @ResponseBody
    fun createCar(@RequestBody car: AddCarDTO): ResponseEntity<Unit> {
        val carDto = carService.addCar(car)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(mapOf("id" to carDto.carid))
            .toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getCarDetails(@PathVariable id: UUID): ResponseEntity<CarResource> {
        return ResponseEntity.ok(resourceAssembler.toModel(carService.getDetails(id)))
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    fun deleteCar(@PathVariable id: UUID) = ResponseEntity.ok(carService.delete(id))

    @ExceptionHandler(value = [(RuntimeException::class)])
    fun handleException(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }
}
