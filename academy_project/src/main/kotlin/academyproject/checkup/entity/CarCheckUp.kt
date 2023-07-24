package academyproject.checkup.entity

import academyproject.car.entity.Car
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "carcheckups")
data class CarCheckUp(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "datetime")
    val dateTime: LocalDateTime,
    val worker: String,
    val price: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carid")
    val car: Car,
)
