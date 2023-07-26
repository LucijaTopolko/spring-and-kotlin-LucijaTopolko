package academyproject.car.service

import academyproject.car.controller.dto.ManufacturerResponse
import academyproject.car.entity.Model
import academyproject.car.repository.ModelRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class FetchService(
    private val webClient: WebClient,
    @Value("\${models-service.base-url}") baseUrl: String,
    private val modelRepository: ModelRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    val responseEntity = webClient.get().retrieve().bodyToMono(Map::class.java).block()

    @Cacheable("manufacturer")
    fun fetch() {
        webClient
            .get()
            .retrieve()
            .bodyToMono<ManufacturerResponse>()
            .flatMapIterable { manufacturer -> manufacturer.cars }
            .flatMapIterable { car ->
                car.models.map { model ->
                    val inDataBase = modelRepository.findByManufacturerAndModel(car.manufacturer, model)
                    if (inDataBase == null) {
                        val dto = Model(manufacturer = car.manufacturer, model = model)
                        modelRepository.save(dto)
                    }
                }
            }
            .collectList()
            .block() ?: emptyList()
    }
}
