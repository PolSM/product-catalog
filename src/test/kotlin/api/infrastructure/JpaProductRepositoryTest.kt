package api.infrastructure

import api.domain.product.Category
import api.domain.product.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ActiveProfiles("test")
class JpaProductRepositoryTest {

    @Autowired
    private lateinit var jpaProductRepository: JpaProductRepository

    @BeforeEach
    fun setUp() {
        jpaProductRepository.deleteAll()
    }

    @Test
    fun `should findAll without filtering or sorting`() {
        val aProduct = Product(sku = "123", price = 100.0, description = "Product 1", category = Category.ELECTRONICS)
        val anotherProduct =
            Product(sku = "124", price = 200.0, description = "Product 2", category = Category.HOME_KITCHEN)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)

        val products = jpaProductRepository.findAll(null, Pageable.unpaged())

        assertEquals(listOf(aProduct, anotherProduct), products)
    }

    @Test
    fun `should findAll by category`() {
        val aProduct = Product(sku = "123", price = 100.0, description = "Product 1", category = Category.ELECTRONICS)
        val anotherProduct =
            Product(sku = "124", price = 200.0, description = "Product 2", category = Category.HOME_KITCHEN)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)

        val products = jpaProductRepository.findAll(Category.ELECTRONICS, Pageable.unpaged())

        assertEquals(listOf(aProduct), products)
    }

    @Test
    fun `should findAll sorted by sku`() {
        val aProduct = Product(sku = "124", price = 100.0, description = "Product 1", category = Category.ELECTRONICS)
        val anotherProduct =
            Product(sku = "123", price = 200.0, description = "Product 2", category = Category.HOME_KITCHEN)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)

        val products = jpaProductRepository.findAll(null, Pageable.unpaged(Sort.by("sku")))

        assertEquals(listOf(anotherProduct, aProduct), products)
    }

    @Test
    fun `should findAll sorted by price`() {
        val aProduct = Product(sku = "123", price = 200.0, description = "Product 1", category = Category.ELECTRONICS)
        val anotherProduct =
            Product(sku = "124", price = 100.0, description = "Product 2", category = Category.HOME_KITCHEN)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)

        val products = jpaProductRepository.findAll(null, Pageable.unpaged(Sort.by("price")))

        assertEquals(listOf(anotherProduct, aProduct), products)
    }

    @Test
    fun `should findAll sorted by description`() {
        val aProduct = Product(sku = "123", price = 100.0, description = "Product 2", category = Category.ELECTRONICS)
        val anotherProduct =
            Product(sku = "124", price = 200.0, description = "Product 1", category = Category.HOME_KITCHEN)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)

        val products = jpaProductRepository.findAll(null, Pageable.unpaged(Sort.by("description")))

        assertEquals(listOf(anotherProduct, aProduct), products)
    }

    @Test
    fun `should findAll sorted by category`() {
        val aProduct = Product(sku = "123", price = 100.0, description = "Product 1", category = Category.HOME_KITCHEN)
        val anotherProduct =
            Product(sku = "124", price = 200.0, description = "Product 2", category = Category.ELECTRONICS)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)

        val products = jpaProductRepository.findAll(null, Pageable.unpaged(Sort.by("category")))

        assertEquals(listOf(anotherProduct, aProduct), products)
    }

    @Test
    fun `should findAll by category and sorted by price`() {
        val aProduct = Product(sku = "123", price = 200.0, description = "Product 1", category = Category.ELECTRONICS)
        val anotherProduct =
            Product(sku = "124", price = 100.0, description = "Product 2", category = Category.HOME_KITCHEN)
        val anotherProduct2 =
            Product(sku = "125", price = 100.0, description = "Product 2", category = Category.ELECTRONICS)
        jpaProductRepository.save(aProduct)
        jpaProductRepository.save(anotherProduct)
        jpaProductRepository.save(anotherProduct2)

        val products = jpaProductRepository.findAll(Category.ELECTRONICS, Pageable.unpaged(Sort.by("price")))

        assertEquals(listOf(anotherProduct2, aProduct), products)
    }
}