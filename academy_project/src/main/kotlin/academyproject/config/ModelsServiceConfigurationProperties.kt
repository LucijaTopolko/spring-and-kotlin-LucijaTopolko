package academyproject.config

import org.hibernate.validator.constraints.URL
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "models-service")
@ConstructorBinding
@Validated
data class ModelsServiceConfigurationProperties(
    @field:URL
    val baseUrl: String
)
