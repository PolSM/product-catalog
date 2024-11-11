package api.presentation

import api.application.ProductService
import api.domain.product.Category
import api.domain.product.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getProducts(
        @RequestParam(required = false) category: Category?,
        @RequestParam(required = false, defaultValue = "sku") sortBy: String
    ): ResponseEntity<String> {
        val products = productService.getProducts(category, sortBy)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("UP")
    }

}