package api.presentation

import api.application.ProductService
import api.domain.product.Category
import api.domain.product.Product
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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
        `when`(productService.getProducts(null, "sku")).thenReturn(Gson().toJson(products))

        val expectedJson = """
            [
                {"sku": "SKU0001", "price": 19.99, "description": "Wireless Mouse with ergonomic design", "category": "ELECTRONICS"},
                {"sku": "SKU0002", "price": 499.00, "description": "4K Ultra HD Smart TV, 55 inches", "category": "ELECTRONICS"}
            ]
        """.trimIndent()

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().json(expectedJson))
    }
}