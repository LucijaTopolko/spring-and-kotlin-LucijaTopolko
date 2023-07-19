package academyproject
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.annotation.Commit
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class CheckUpControllerTest {

    @Autowired
    lateinit var template: NamedParameterJdbcTemplate

    @BeforeEach
    fun setUp() {
        template.update(
            "INSERT INTO cars (date, manufacturer, model, year, vin) VALUES (:date, :manufacturer, :model, :year, :vin)",
            mapOf("date" to LocalDate.now(), "manufacturer" to "BMW", "model" to "X6", "year" to Year.of(2020).value, "vin" to "HR123456789"),
        )
        template.update(
            "INSERT INTO carCheckUps (dateTime, worker, price, carId) VALUES (:dateTime, :worker, :price, :carId)",
            mapOf("dateTime" to LocalDateTime.now(), "worker" to "John", "price" to 150, "carId" to 1),
        )
    }

    @Test
    fun addingCars() {
        Assertions.assertThat(
            template.queryForObject(
                "SELECT model FROM cars WHERE carId=:carId",
                mapOf("carId" to 1),
                String::class.java,
            ),
        ).isEqualTo("X6")
    }

    @Test
    fun addingCheckUps() {
        Assertions.assertThat(
            template.queryForObject(
                "SELECT price FROM carCheckUps WHERE worker = :worker ORDER BY dateTime DESC LIMIT 1",
                mapOf("worker" to "John"),
                Int::class.java,
            ),
        ).isEqualTo(150)
    }
}
