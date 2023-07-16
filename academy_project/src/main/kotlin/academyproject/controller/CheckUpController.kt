package academyproject.controller

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.entities.CarNotFoundException
import academyproject.service.CheckUpService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports

@Controller
class CheckUpController(private val checkUppService: CheckUpService) {

    @PostMapping("/add-car")
    @ResponseBody
    fun createCar(@RequestBody car: Car): ResponseEntity<Car> {
        val newCar: Car = checkUppService.addCar(car)
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    @PostMapping("/add-checkup")
    @ResponseBody
    fun createCheckUp(@RequestBody checkUp: CarCheckUp): ResponseEntity<CarCheckUp> {
        val newCheckUp: CarCheckUp = checkUppService.addCheckUp(checkUp)
        return ResponseEntity(newCheckUp, HttpStatus.OK)
    }

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println(ex.message)
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/car-details/{vin}")
    @ResponseBody
    fun getCarDetails(@PathVariable vin: String): ResponseEntity<Car> {
        println(vin)
        val car = checkUppService.getCheckUps(vin.removeSurrounding("\""))
        return ResponseEntity(car, HttpStatus.OK)
    }

    @GetMapping("/manufacturers-details")
    @ResponseBody
    fun getManufacturerDetails(): ResponseEntity<Map<String, Int>> {
        val details = checkUppService.getManufacturers()
        return ResponseEntity(details, HttpStatus.OK)
    }
}
