package src.main.kotlin

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

@Configuration
@ComponentScan
@PropertySource("classpath:info.properties")
class ApplicationConfiguration(private val dataSource: DataSource) {

    @Value("\${switch}")
    private lateinit var switch: String
    @Bean
    fun carCheckUpsResource(): Resource {
        return FileSystemResource(dataSource.dbName)
    }

    @Bean
    fun carCheckUpRepository(): CarCheckUpRepository {
        return if (switch == "1") {
            InMemoryCarCheckUpRepository(dataSource)
        } else {
            InFileCarCheckUpRepository(carCheckUpsResource())
        }
    }
}
