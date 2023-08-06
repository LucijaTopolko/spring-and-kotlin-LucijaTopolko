package academyproject.car.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Manufacturers(
    @JsonProperty("manufacturer") val manufacturer: String,
    @JsonProperty("models") val models: List<String>
)
