package academyproject.checkup.controller

import academyproject.checkup.controller.dto.AddCheckUpDTO
import academyproject.checkup.service.CheckUpService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RequestMapping("/api/v1/checkup")
@Controller
class CheckUpController(
    private val checkUpService: CheckUpService,
) {

    @PostMapping
    @ResponseBody
    fun createCar(@RequestBody checkUp: AddCheckUpDTO): ResponseEntity<Unit> {
        checkUpService.addCheckUp(checkUp)
        val location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/car/{id}/checkup")
            .buildAndExpand(mapOf("id" to checkUp.carid))
            .toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/manufacturers-details")
    @ResponseBody
    fun getManufacturerDetails() =
        ResponseEntity.ok(checkUpService.manufacturerDetails())

    @ExceptionHandler(value = [(RuntimeException::class)])
    fun handleException(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity("Error occurred: ${ex.message}", HttpStatus.NOT_FOUND)
    }
}
