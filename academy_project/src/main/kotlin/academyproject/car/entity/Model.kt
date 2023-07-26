package academyproject.car.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "carinfo")
data class Model(
    @Id
    @GeneratedValue
    val modelid: UUID = UUID.randomUUID(),

    val manufacturer: String,
    val model: String,
)
