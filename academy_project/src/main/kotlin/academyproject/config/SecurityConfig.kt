package academyproject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { }
            csrf { disable() }
            authorizeRequests {
                authorize("/api/v1/car/paged", hasRole("ADMIN"))
                authorize("/api/v1/car", hasAnyRole("ADMIN", "USER"))
                authorize("/api/v1/car/delete/{id}", hasRole("ADMIN"))
                authorize("/api/v1/car/{id}", hasAnyRole("ADMIN", "USER"))
                authorize("/api/v1/car/{id}/checkup", hasAnyRole("ADMIN", "USER"))
                authorize("/api/v1/checkup", hasRole("ADMIN"))
                authorize("/api/v1/checkup/delete/{id}", hasRole("ADMIN"))
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }
}
