package academyproject.checkup.service

import academyproject.car.repository.CarRepository
import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.controller.dto.CheckUpDTO
import academyproject.checkup.entity.CarCheckUp
import academyproject.checkup.repository.CheckUpRepository
import academyproject.exception.entity.CarNotFoundException
import academyproject.exception.entity.CheckUpNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CheckUpService(
    private val checkUpRepository: CheckUpRepository,
    private val carRepository: CarRepository,
) {

    fun addCheckUp(checkUp: AddCheckUpDTO): CheckUpDTO {
        val new = checkUp.checkup { carid ->
            carRepository.findById(carid)
                ?: throw CarNotFoundException()
        }
        return CheckUpDTO(checkUpRepository.save(new))
    }

    fun manufacturerDetails(): Map<String, Int> {
        val res = checkUpRepository.countByManufacturer()
        val map: MutableMap<String, Int> = mutableMapOf()

        for (proj in res) {
            val manufacturer = proj.getManufacturer()
            val count = proj.getCount()

            map[manufacturer] = count
        }
        return map
    }

    fun getAll(sort: String?, carId: UUID, pageable: Pageable): Page<CarCheckUp> {
        val car = carRepository.findById(carId) ?: throw CarNotFoundException()
        return if (sort == "desc") {
            checkUpRepository.findByCarOrderByDateTimeDesc(car, pageable)
        } else {
            checkUpRepository.findByCarOrderByDateTimeAsc(car, pageable)
        }
    }

    fun deleteCheckUp(id: UUID) {
        if (!checkUpRepository.existsById(id)) throw CheckUpNotFoundException()
        checkUpRepository.deleteById(id)
    }

}
