package academyproject.repository

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.entities.CarNotFoundException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CheckUpRepository(private val template: NamedParameterJdbcTemplate) {

    fun insertCar(car: Car) {
        template.update(
            "INSERT INTO cars (date, manufacturer, model, year, vin) VALUES (:date, :manufacturer, :model, :year, :vin)",
            mapOf("date" to car.date, "manufacturer" to car.manufacturer, "model" to car.model, "year" to car.year.value, "vin" to car.vin),
        )
    }

    fun insertCheckUp(checkUp: CarCheckUp) {
        val isCheckUpNecessary = template.queryForObject(
            "SELECT isCheckUpNecessary FROM cars WHERE carId = :carId",
            mapOf("carId" to checkUp.carId),
            Boolean::class.java,
        ) ?: throw CarNotFoundException()

        template.update(
            "INSERT INTO carCheckUps (dateTime, worker, price, carId) VALUES (:dateTime, :worker, :price, :carId)",
            mapOf("dateTime" to checkUp.dateTime, "worker" to checkUp.worker, "price" to checkUp.price, "carId" to checkUp.carId),
        )

        check(checkUp.carId, isCheckUpNecessary)
    }

    fun check(carId: Long, isCheckUpNecessary: Boolean) {
        val needed = template.queryForObject(
            "SELECT MAX(dateTime) FROM carCheckUps WHERE carId = :carId",
            mapOf("carId" to carId),
            LocalDateTime::class.java,
        )?.isAfter(LocalDateTime.now().minusYears(1)) == true

        if (needed != isCheckUpNecessary) {
            template.update(
                "UPDATE cars SET isCheckUpNecessary = :needed WHERE carId = :carId",
                mapOf("needed" to needed, "carId" to carId),
            )
        }
    }
}
