package src.main.kotlin

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DataSource(
    @Value("\${db.dbName}") val dbName: String,
    @Value("\${db.username}") val username: String,
    @Value("\${db.password}") val password: String,
)
