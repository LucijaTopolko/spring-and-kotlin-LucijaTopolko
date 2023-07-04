package src.main.kotlin
import java.time.LocalDateTime

class Car(manufacturer:String, model:String, vin:String){
    val manufacturer: String
    private val model: String
    val vin: String

    init {
        this.manufacturer=manufacturer
        this.model=model
        this.vin=vin
    }
}

class CarCheckUp(performedAt: LocalDateTime, car: Car) {
    val performedAt: LocalDateTime
    val car: Car

    init {
        this.performedAt=performedAt
        this.car=car
    }
}

object CarCheckUpSystem {
    private val car1: Car = Car("Porsche", "Panamera", "WP0AB2A73EL050316")
    private val car2: Car = Car("BMW", "X6", "WP0AA29848S711827")
    private val car3: Car = Car("BMW", "X6", "WP0JB0932FS859246")
    private val car4: Car = Car("Tesla", "Model Y", "WP0BA2999XS654710")

    private val checkups: MutableList<CarCheckUp> = mutableListOf(
        CarCheckUp(LocalDateTime.of(2021,8,14,15,20), car1),
        CarCheckUp(LocalDateTime.of(2021,12,15,11,36), car1),
        CarCheckUp(LocalDateTime.of(2021,12,15,15,11), car2),
        CarCheckUp(LocalDateTime.of(2022,1,24,9,19), car4),
        CarCheckUp(LocalDateTime.of(2022,3,11,11,24), car3),
        CarCheckUp(LocalDateTime.of(2022,7,1,13,15), car2),
        CarCheckUp(LocalDateTime.of(2022,11,14,12,27), car3),
        CarCheckUp(LocalDateTime.of(2023,2,11,10,29), car3),
        CarCheckUp(LocalDateTime.of(2023,2,19,14,57), car4),
        CarCheckUp(LocalDateTime.of(2023,5,14,14,48), car1)
    )

    fun isCheckUpNecessary(vin: String):Boolean {
        val checkUpList = getCheckUps(vin)
        val timeMark = LocalDateTime.now().minusYears(1)
        val latest = checkUpList.maxByOrNull { checkUp -> checkUp.performedAt }
        return latest?.performedAt?.isBefore(timeMark)?:false
    }

    fun addCheckUp(vin: String):CarCheckUp {
        val car = when(vin) {
            car1.vin -> car1
            car2.vin -> car2
            car3.vin -> car3
            car4.vin -> car4
            else -> throw Exception("There is no car with given VIN!")
        }
        val checkUp = CarCheckUp(LocalDateTime.now(), car)
        checkups.add(checkUp)
        return checkUp
    }

    private fun getCheckUps(vin: String):List<CarCheckUp> {
        val checkUpsFiltered = checkups.filter { checkUp -> checkUp.car.vin == vin }
        if (checkUpsFiltered.isEmpty())
            throw Exception("There is no car with given VIN!")
        return checkUpsFiltered
    }

    fun countCheckUps(manufacturer: String):Int {
        return checkups.count { checkup -> checkup.car.manufacturer == manufacturer }
    }
}

/* fun main() {
    val test = CarCheckUpSystem
    println(test.isCheckUpNecessary("WP0AA29848S711827"))
    println(test.countCheckUps("BMW"))
    println(test.addCheckUp("WP0AA29848S711827").performedAt)
    println(test.isCheckUpNecessary("WP0AA29848S711827"))
    println(test.countCheckUps("BMW"))
} */