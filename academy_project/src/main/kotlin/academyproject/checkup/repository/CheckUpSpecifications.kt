package academyproject.checkup.repository

import academyproject.car.entity.Car
import academyproject.checkup.controller.dto.CheckUpFilter
import academyproject.checkup.entity.CarCheckUp
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime

object CheckUpSpecifications {

    fun isDateTime(dateTime: LocalDateTime) = Specification<CarCheckUp> { root, query, cb ->
        cb.equal(root.get<LocalDateTime>(CarCheckUp::dateTime.name), dateTime)
    }

    fun isWorker(worker: String) = Specification<CarCheckUp> { root, query, cb ->
        cb.equal(root.get<String>(CarCheckUp::worker.name), worker)
    }

    fun isPrice(price: Int) = Specification<CarCheckUp> { root, query, cb ->
        cb.equal(root.get<Int>(CarCheckUp::price.name), price)
    }

    fun isCar(car: Car) = Specification<CarCheckUp> { root, query, cb ->
        cb.equal(root.get<Car>(CarCheckUp::car.name), car)
    }

    fun toSpecification(filter: CheckUpFilter): Specification<CarCheckUp> {
        var spec = Specification<CarCheckUp> { root, query, cb ->
            cb.and()
        }

        if (filter.dateTime != null) {
            spec = spec.and(isDateTime(filter.dateTime))
        }

        if (filter.worker != null) {
            spec = spec.and(isWorker(filter.worker))
        }

        if (filter.price != null) {
            spec = spec.and(isPrice(filter.price))
        }

        if (filter.car != null) {
            spec = spec.and(isCar(filter.car!!))
        }

        return spec
    }
}
