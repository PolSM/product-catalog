package api.application

import api.domain.product.Category
import api.domain.product.ProductRepository
import org.springframework.stereotype.Service
import com.google.gson.Gson

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProducts(category: Category?, sortBy: String): String {
        val products = productRepository.findAll(category, null)

        val discountedProducts = products.map { product ->
            val discount = when {
                product.sku.endsWith("5") -> 0.30
                product.category == Category.HOME_KITCHEN -> 0.25
                product.category == Category.ELECTRONICS -> 0.15
                else -> 0.0
            }
            product.copy(price = product.price * (1 - discount))
        }
        return Gson().toJson(discountedProducts)
    }
}