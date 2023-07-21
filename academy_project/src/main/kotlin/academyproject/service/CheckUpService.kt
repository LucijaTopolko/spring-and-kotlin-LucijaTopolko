package academyproject.service

import academyproject.entities.CarCheckUp
import academyproject.repository.CheckUpRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CheckUpService(private val checkUpRepository: CheckUpRepository) {

    @Transactional
    fun addCheckUp(checkUp: CarCheckUp) {
        checkUpRepository.insertCheckUp(checkUp)
    }

    @Transactional
    fun manufacturerDetails(): Map<String, Int> {
        return checkUpRepository.getManufacturerDetails()
    }
}
