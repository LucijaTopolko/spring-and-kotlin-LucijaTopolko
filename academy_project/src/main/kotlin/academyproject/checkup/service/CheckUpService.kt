package academyproject.checkup.service

import academyproject.car.repository.CarRepository
import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.controller.dto.CheckUpDTO
import academyproject.checkup.controller.dto.CheckUpFilter
import academyproject.checkup.entity.CarCheckUp
import academyproject.checkup.repository.CheckUpRepository
import academyproject.checkup.repository.CheckUpSpecifications
import academyproject.exception.entity.CarNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

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

    fun getAll(filter: CheckUpFilter, pageable: Pageable): Page<CarCheckUp> {
        val spec = CheckUpSpecifications.toSpecification(filter)
        return checkUpRepository.findAll(spec, pageable)
    }
}
