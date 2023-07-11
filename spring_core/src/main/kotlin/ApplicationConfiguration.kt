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
class ApplicationConfiguration {
    @Value("\${db.dbName}")
    private lateinit var dbName: String

    @Value("\${db.username}")
    private lateinit var username: String

    @Value("\${db.password}")
    private lateinit var password: String

    @Value("\${switch}")
    private lateinit var switch: String

    @Bean
    fun dataSource(): DataSource {
        return DataSource(dbName, username, password)
    }

    @Bean
    fun carCheckUpsResource(): Resource {
        return FileSystemResource(dbName)
    }

    @Bean
    fun carCheckUpRepository(): CarCheckUpRepository {
        return if (switch == "1") {
            InMemoryCarCheckUpRepository(dataSource())
        } else {
            InFileCarCheckUpRepository(carCheckUpsResource())
        }
    }
}
