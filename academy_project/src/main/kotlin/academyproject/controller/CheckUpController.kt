package academyproject.controller

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.entities.CarNotFoundException
import academyproject.service.CarService
import academyproject.service.CheckUpService
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports

@Controller
class CheckUpController(
    private val checkUppService: CheckUpService,
    private val carService: CarService,
    private val template: JdbcTemplate,
) {

    @PostMapping("/add-car")
    @ResponseBody
    fun createCar(@RequestBody car: Car): ResponseEntity<Unit> {
        carService.addCar(car)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/add-checkup")
    @ResponseBody
    fun createCheckUp(@RequestBody checkUp: CarCheckUp): ResponseEntity<Unit> {
        checkUppService.addCheckUp(checkUp)
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println(ex.message)
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/car-details/{vin}")
    @ResponseBody
    fun getCarDetails(@PathVariable vin: String): ResponseEntity<Car?> {
        try {
            val car = carService.getDetails(vin)
            return ResponseEntity(car, HttpStatus.OK)
        } catch (ex: IncorrectResultSizeDataAccessException) {
            throw CarNotFoundException()
        }
    }

    @GetMapping("/manufacturers-details")
    @ResponseBody
    fun getManufacturerDetails(): ResponseEntity<Map<String, Int>> {
        val details = checkUppService.manufacturerDetails()
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(details)
    }
}
