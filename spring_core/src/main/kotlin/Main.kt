package src.main.kotlin

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import java.time.LocalDateTime

fun main() {
    val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(ApplicationConfiguration::class.java)
    val carCheckUp: CheckUpService = applicationContext.getBean(CheckUpService::class.java)

    val car = Car("Porsche", "Panamera", "WP0AB2A73EL050316")

    println(carCheckUp.insert(LocalDateTime.now(), car))
    println(carCheckUp.findAll().entries)
    println(carCheckUp.findById(1).car.vin)
    // println(carCheckUp.deleteById(2)) -- throws error in first use
}
