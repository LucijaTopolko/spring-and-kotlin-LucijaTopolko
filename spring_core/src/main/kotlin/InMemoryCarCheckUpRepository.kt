package src.main.kotlin

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class InMemoryCarCheckUpRepository(private val dataSource: DataSource) : CarCheckUpRepository {

    init {
        println("dbName: ${dataSource.dbName}")
        println("username: ${dataSource.username}")
        println("password: ${dataSource.password}")
    }

    private val checkUps = mutableMapOf<Long, CarCheckUp>()

    override fun insert(performedAt: LocalDateTime, car: Car): Long {
        val id = (this.checkUps.keys.maxOrNull() ?: 0) + 1
        this.checkUps[id] = CarCheckUp(id, performedAt, car)
        return id
    }

    override fun findById(id: Long): CarCheckUp {
        return this.checkUps[id] ?: throw CarCheckUpNotFoundException(id)
    }

    override fun deleteById(id: Long): CarCheckUp {
        return this.checkUps.remove(id) ?: throw CarCheckUpNotFoundException(id)
    }

    override fun findAll(): Map<Long, CarCheckUp> {
        return this.checkUps.toMap()
    }
}
