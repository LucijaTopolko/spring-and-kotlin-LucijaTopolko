package src.test.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import src.main.kotlin.ApplicationConfiguration
import src.main.kotlin.CheckUpService

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApplicationConfiguration::class])
class MainIntegrationTest @Autowired constructor(
    private var applicationContext: ApplicationContext,
) {

    @Test
    fun verifyServiceBean() {
        assertThat(applicationContext.getBean(CheckUpService::class.java)).isNotNull
        assertThat(applicationContext.getBean(CheckUpService::class.java).equals(2))
    }

    @Test
    fun contextInitialization() {
        assertThat(applicationContext).isNotNull
    }
}
