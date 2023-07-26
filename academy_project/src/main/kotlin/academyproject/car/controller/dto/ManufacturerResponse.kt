package academyproject.car.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ManufacturerResponse(
    @JsonProperty("cars") val cars: List<Manufacturers>,
)
