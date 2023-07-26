package academyproject.car.controller

import academyproject.car.controller.dto.AddCarDTO
import academyproject.car.service.CarService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports

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

    @GetMapping("/{vin}")
    @ResponseBody
    fun getCarDetails(@PathVariable vin: String) =
        ResponseEntity.ok(carService.getDetails(vin))
}
