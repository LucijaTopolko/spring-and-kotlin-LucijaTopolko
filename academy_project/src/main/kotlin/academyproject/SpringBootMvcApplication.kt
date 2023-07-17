package academyproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootMvcApplication

fun main(args: Array<String>) {
    runApplication<SpringBootMvcApplication>(*args)
}
