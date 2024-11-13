package api.presentation

import api.application.ProductService
import api.domain.product.Category
import api.domain.product.Product
import api.domain.product.ProductsNotFoundException
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Sort
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController::class)
class ProductControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun `should return a list of products`() {
        val products = listOf(
            Product("SKU0001", 19.99, "Wireless Mouse with ergonomic design", Category.ELECTRONICS),
            Product("SKU0002", 499.00, "4K Ultra HD Smart TV, 55 inches", Category.ELECTRONICS)
        )
        `when`(productService.getProducts(null, Sort.unsorted())).thenReturn(Gson().toJson(products))

        val expectedJson = """
            [
                {"sku": "SKU0001", "price": 19.99, "description": "Wireless Mouse with ergonomic design", "category": "Electronics"},
                {"sku": "SKU0002", "price": 499.00, "description": "4K Ultra HD Smart TV, 55 inches", "category": "Electronics"}
            ]
        """.trimIndent()

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().json(expectedJson))
    }
    @Test
    fun `should return 400 for illegal argument`() {
        `when`(productService.getProducts(null, Sort.unsorted())).thenThrow(IllegalArgumentException("Invalid argument"))

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{"path":"/api/products","message":"Illegal argument: Invalid argument"}"""))
    }

    @Test
    fun `should return 400 for method argument type mismatch`() {
        mockMvc.perform(get("/api/products").param("category", "invalidCategory"))
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{"path":"/api/products","message":"Illegal argument: Unknown category: invalidCategory"}"""))
    }

    @Test
    fun `should return 404 when products not found`() {
        `when`(productService.getProducts(null, Sort.unsorted())).thenThrow(ProductsNotFoundException("No products found for the given criteria"))

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isNotFound)
            .andExpect(content().json("""{"path":"/api/products","message":"Products not found: No products found for the given criteria"}"""))
    }
}