package academyproject.service

import academyproject.entities.Car
import academyproject.entities.CarCheckUp
import academyproject.repository.CheckUpRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CheckUpService(private val checkUpRepository: CheckUpRepository) {

    @Transactional
    fun addCar(car: Car) {
        checkUpRepository.insertCar(car)
    }

    @Transactional
    fun addCheckUp(checkUp: CarCheckUp) {
        checkUpRepository.insertCheckUp(checkUp)
    }
}
