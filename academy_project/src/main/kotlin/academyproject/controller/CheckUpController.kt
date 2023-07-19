package academyproject.controller

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.entities.CarNotFoundException
import academyproject.service.CheckUpService
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports
import java.time.Year

@Controller
class CheckUpController(
    private val checkUppService: CheckUpService,
    private val template: JdbcTemplate,
) {

    @PostMapping("/add-car")
    @ResponseBody
    fun createCar(@RequestBody car: Car): ResponseEntity<Unit> {
        checkUppService.addCar(car)
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
            val car = template.queryForObject(
                "SELECT * FROM cars WHERE vin = ?",
                arrayOf(vin),
                RowMapper { resultSet, _ ->
                    Car(
                        carId = resultSet.getLong("carId"),
                        date = resultSet.getDate("date").toLocalDate(),
                        manufacturer = resultSet.getString("manufacturer"),
                        model = resultSet.getString("model"),
                        year = Year.of(resultSet.getInt("year")),
                        vin = resultSet.getString("vin"),
                        isCheckUpNecessary = resultSet.getBoolean("isCheckUpNecessary"),
                    )
                },
            ) ?: throw CarNotFoundException()
            val checkups: MutableList<CarCheckUp> = template.query(
                "SELECT * FROM carCheckUps WHERE carId=?",
                arrayOf(car.carId),
                RowMapper { resultSet, _ ->
                    CarCheckUp(
                        id = resultSet.getLong("id"),
                        dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime(),
                        worker = resultSet.getString("worker"),
                        price = resultSet.getInt("price"),
                        carId = resultSet.getLong("carId"),
                    )
                },
            ).sortedByDescending { checkUp -> checkUp.dateTime } as MutableList<CarCheckUp>
            car.checkUps = checkups
            return ResponseEntity(car, HttpStatus.OK)
        } catch (ex: IncorrectResultSizeDataAccessException) {
            throw CarNotFoundException()
        }
    }

    @GetMapping("/manufacturers-details")
    @ResponseBody
    fun getManufacturerDetails(): ResponseEntity<Map<String, Int>> {
        val details = template.queryForList(
            "SELECT DISTINCT cars.manufacturer, (SELECT COUNT(*) FROM carCheckUps WHERE carId = cars.carId) FROM cars",
        ).associate { it["manufacturer"] as String to (it["count"] as Number).toInt() }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(details)
    }
}
