package api.product_catalog.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController {

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("UP")
    }


}