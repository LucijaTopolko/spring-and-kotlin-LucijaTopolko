package academyproject.repository

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.entities.CarNotFoundException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.Year

@Repository
class CarRepository(private val template: NamedParameterJdbcTemplate) {

    fun insertCar(car: Car) {
        template.update(
            "INSERT INTO cars (date, manufacturer, model, year, vin) VALUES (:date, :manufacturer, :model, :year, :vin)",
            mapOf("date" to car.date, "manufacturer" to car.manufacturer, "model" to car.model, "year" to car.year.value, "vin" to car.vin),
        )
    }

    fun getCarDetails(vin: String): Car {
        val car = template.queryForObject(
            "SELECT * FROM cars WHERE vin = :vin",
            mapOf("vin" to vin),
        ) { resultSet, _ ->
            Car(
                carId = resultSet.getLong("carId"),
                date = resultSet.getDate("date").toLocalDate(),
                manufacturer = resultSet.getString("manufacturer"),
                model = resultSet.getString("model"),
                year = Year.of(resultSet.getInt("year")),
                vin = resultSet.getString("vin"),
                isCheckUpNecessary = resultSet.getBoolean("isCheckUpNecessary"),
            )
        } ?: throw CarNotFoundException()
        val checkups: MutableList<CarCheckUp> = template.query(
            "SELECT * FROM carCheckUps WHERE carId=:carId",
            mapOf("carId" to car.carId),
        ) { resultSet, _ ->
            CarCheckUp(
                id = resultSet.getLong("id"),
                dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime(),
                worker = resultSet.getString("worker"),
                price = resultSet.getInt("price"),
                carId = resultSet.getLong("carId"),
            )
        }.sortedByDescending { checkUp -> checkUp.dateTime } as MutableList<CarCheckUp>
        car.checkUps = checkups
        return car
    }
}
