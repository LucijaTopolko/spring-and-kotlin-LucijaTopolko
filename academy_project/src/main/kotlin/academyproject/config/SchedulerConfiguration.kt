package academyproject.config

import academyproject.car.service.FetchService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulerConfiguration(private val fetchService: FetchService) {

    @Scheduled(fixedRate = 86_400_000)
    fun scheduling() {
        fetchService.fetch()
    }
}
