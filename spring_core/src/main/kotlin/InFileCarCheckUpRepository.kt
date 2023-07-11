package src.main.kotlin

import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.FileOutputStream
import java.time.LocalDateTime

@Component
class InFileCarCheckUpRepository(private val carCheckUpsFileResource: Resource) : CarCheckUpRepository {

    init {
        if (!carCheckUpsFileResource.exists()) {
            carCheckUpsFileResource.file.createNewFile()
        }
    }

    override fun insert(performedAt: LocalDateTime, car: Car): Long {
        val id = (
            carCheckUpsFileResource.file.readLines()
                .filter { line -> line.isNotEmpty() }
                .maxOfOrNull { line -> line.split(",")[0].toLong() } ?: 0
            ) + 1
        carCheckUpsFileResource.file.appendText("$id,${car.vin},${car.manufacturer},${car.model},$performedAt\n")
        return id
    }

    override fun findById(id: Long): CarCheckUp {
        return carCheckUpsFileResource.file.readLines()
            .filter { line -> line.isNotEmpty() }
            .find { line -> line.split(",")[0].toLong() == id }
            ?.toCarCheckUp() ?: throw CarCheckUpNotFoundException(id)
    }

    override fun deleteById(id: Long): CarCheckUp {
        val checkUpLines = carCheckUpsFileResource.file.readLines()
        var deletedLine: String? = null
        FileOutputStream(carCheckUpsFileResource.file).writer()
            .use { fileOutputWriter ->
                checkUpLines.forEach { line ->
                    if (line.split(",").first().toLong() == id) {
                        deletedLine = line
                    } else {
                        fileOutputWriter.appendLine(line)
                    }
                }
            }
        return deletedLine?.toCarCheckUp() ?: throw
            CarCheckUpNotFoundException(id)
    }

    override fun findAll(): Map<Long, CarCheckUp> {
        return carCheckUpsFileResource.file.readLines().map {
                line ->
            line.toCarCheckUp()
        }.associateBy { line -> line.id }
    }

    private fun String.toCarCheckUp(): CarCheckUp {
        val parts = this.split(",")
        return CarCheckUp(
            parts[0].toLong(),
            LocalDateTime.parse(parts[4]),
            Car(parts[2], parts[3], parts[1]),
        )
    }
}
