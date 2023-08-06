package academyproject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class HttpConfiguration {

    @Bean
    fun provideWebClient(
        webClientBuilder: WebClient.Builder,
        modelsServiceConfigurationProperties: ModelsServiceConfigurationProperties
    ) = webClientBuilder.baseUrl(modelsServiceConfigurationProperties.baseUrl).build()
}
