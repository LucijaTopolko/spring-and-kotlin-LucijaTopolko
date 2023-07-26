package academyproject.car.controller.dto

data class ManufacturerWithModelsDTO(
    val manufacturer: String,
    val models: List<String>,
) : java.io.Serializable
