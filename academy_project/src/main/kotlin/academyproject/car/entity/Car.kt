package academyproject.car.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.* // ktlint-disable no-wildcard-imports

@Entity
@Table(name = "cars")
data class Car(
    @Id
    @GeneratedValue
    @Column(name = "carid")
    var carid: UUID = UUID.randomUUID(),

    val date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelid")
    val model: Model,

    var year: Int,
    val vin: String,
)
