package api.presentation

import api.application.ProductService
import api.domain.product.CategoryConverter
import api.domain.product.ProductsNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    private val log: Logger = LogManager.getLogger(ProductController::class.java)

    @GetMapping
    fun getProducts(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) sort: String?,
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): ResponseEntity<String> {
        val pageable: Pageable = createPagination(sort, page, size)
        val products = productService.getProducts(
            CategoryConverter.convertToEntityAttribute(category),
            pageable
        )
        return ResponseEntity.ok(products)
    }

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("UP")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(req: HttpServletRequest, ex: IllegalArgumentException): ResponseEntity<Map<String, Any>> {
        log.error("Illegal argument error occurred on request: ${req.servletPath}", ex)
        val errorResponse = mutableMapOf<String, Any>(
            "path" to req.requestURI,
            "message" to "Illegal argument: ${ex.message}"
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentTypeMismatchException(req: HttpServletRequest, ex: MethodArgumentTypeMismatchException): ResponseEntity<Map<String, Any>> {
        log.error("Method argument type mismatch error occurred on request: " + req.servletPath, ex)
        val errorResponse = mutableMapOf<String, Any>(
            "path" to req.requestURI,
            "message" to "Method argument type mismatch: " + ex.name
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProductsNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProductsNotFoundException(req: HttpServletRequest, ex: ProductsNotFoundException): ResponseEntity<Map<String, Any>> {
        log.error("Products not found error occurred on request: " + req.servletPath, ex)
        val errorResponse = mutableMapOf<String, Any>(
            "path" to req.requestURI,
            "message" to "Products not found: " + ex.message
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    private fun createPagination(
        sort: String?,
        page: Int?,
        size: Int?
    ): Pageable {
        val sortObj = if (sort.isNullOrEmpty()) Sort.unsorted() else Sort.by(sort)
        val pageable: Pageable = if (page != null && size != null) {
            PageRequest.of(page, size, sortObj)
        } else {
            Pageable.unpaged(sortObj)
        }
        return pageable
    }

}