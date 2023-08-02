package academyproject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
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
            formLogin {}
            oauth2Client {}
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager().apply {
            createUser(
                User.withUsername("admin")
                    .password("{bcrypt}$2a$12$7BqDw7tBeVDv5TXndJ1XGeavn00WLFvqP958cM9P9Z6tgphPsV0Ei")
                    .authorities("ROLE_ADMIN").build(),
            )

            createUser(
                User.withUsername("user")
                    .password("{bcrypt}\$2a\$12\$tTvUJAhoUExOnxj12czUouB63tVtIF33HLFwxfY8aS8F01PFc7O5i")
                    .authorities("ROLE_USER").build(),
            )
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}
