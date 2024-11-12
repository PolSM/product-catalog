package api.application

import api.domain.product.Category
import api.domain.product.Product
import api.domain.product.ProductRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class ProductServiceTest {

    private lateinit var productService: ProductService
    private val productRepository: ProductRepository = Mockito.mock(ProductRepository::class.java)

    @BeforeEach
    fun setUp() {
        productService = ProductService(productRepository)
    }

    @Test
    fun `should apply 15% discount for Electronics category`() {
        val products = listOf(
            Product(
                sku = "SKU0001",
                price = 100.0,
                description = "Wireless Mouse",
                category = Category.ELECTRONICS
            ),
        )
        `when`(productRepository.findAll(null, null)).thenReturn(products)

        val discountedProducts = productService.getProducts(null, "sku")

        val expectedJson = """[{"sku":"SKU0001","price":85.0,"description":"Wireless Mouse","category":"ELECTRONICS"}]"""
        assertEquals(expectedJson, discountedProducts)
    }

    @Test
    fun `should apply 25% discount for Home & Kitchen category`() {
        val products = listOf(
            Product(
                sku = "SKU0002",
                price = 100.0,
                description = "Blender",
                category = Category.HOME_KITCHEN
            )
        )
        `when`(productRepository.findAll(null, null)).thenReturn(products)

        val discountedProducts = productService.getProducts(null, "sku")

        val expectedJson = """[{"sku":"SKU0002","price":75.0,"description":"Blender","category":"HOME_KITCHEN"}]""".trimIndent()
        assertEquals(expectedJson, discountedProducts)
    }

    @Test
    fun `should apply 30% discount for SKUs ending in 5`() {
        val products = listOf(
            Product(
                sku = "SKU0005",
                price = 100.0,
                description = "Special Item",
                category = Category.ELECTRONICS
            )
        )
        `when`(productRepository.findAll(null, null)).thenReturn(products)

        val discountedProducts = productService.getProducts(null, "sku")

        val expectedJson = """[{"sku":"SKU0005","price":70.0,"description":"Special Item","category":"ELECTRONICS"}]""".trimIndent()
        assertEquals(expectedJson, discountedProducts)
    }

    @Test
    fun `should apply no discount for other categories`() {
        val products = listOf(
            Product(
                sku = "SKU0003",
                price = 100.0,
                description = "Regular Item",
                category = Category.SPORTS
            )
        )
        `when`(productRepository.findAll(null, null)).thenReturn(products)

        val discountedProducts = productService.getProducts(null, "sku")

        val expectedJson = """[{"sku":"SKU0003","price":100.0,"description":"Regular Item","category":"SPORTS"}]""".trimIndent()
        assertEquals(expectedJson, discountedProducts)
    }

    @Test
    fun `should return a list of products with all the type of discounts`() {
        val products = listOf(
            Product("SKU0001", 100.00, "Wireless Mouse with ergonomic design", Category.ELECTRONICS),
            Product("SKU0005", 100.00, "4K Ultra HD Smart TV, 55 inches", Category.ELECTRONICS),
            Product("SKU0003", 100.00, "Blender with 10 speeds", Category.HOME_KITCHEN),
        )
        `when`(productRepository.findAll(null, null)).thenReturn(products)

        val result = productService.getProducts(null, "sku")

        val expectedJson = Gson().toJson(listOf(
            Product("SKU0001", 85.00, "Wireless Mouse with ergonomic design", Category.ELECTRONICS),
            Product("SKU0005", 70.00, "4K Ultra HD Smart TV, 55 inches", Category.ELECTRONICS),
            Product("SKU0003", 75.00, "Blender with 10 speeds", Category.HOME_KITCHEN),
        ))
        assertEquals(expectedJson, result)
    }
}