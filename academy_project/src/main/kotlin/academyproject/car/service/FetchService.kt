package academyproject.car.service

import academyproject.car.controller.dto.ManufacturerResponse
import academyproject.car.entity.Model
import academyproject.car.repository.ModelRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class FetchService(
    private val webClient: WebClient,
    private val modelRepository: ModelRepository
) {

    fun fetch() {
        webClient
            .get()
            .retrieve()
            .bodyToMono<ManufacturerResponse>()
            .flatMapIterable { manufacturer -> manufacturer.cars }
            .flatMapIterable { car ->
                car.models.map { model ->
                    val inDataBase = modelRepository.existsByManufacturerAndModel(car.manufacturer, model)
                    if (!inDataBase) {
                        val dto = Model(manufacturer = car.manufacturer, model = model)
                        modelRepository.save(dto)
                    }
                }
            }
            .collectList()
            .block() ?: emptyList()
    }
}
