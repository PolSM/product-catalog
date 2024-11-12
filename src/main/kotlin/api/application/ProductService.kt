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
            product.calculateDiscountedPrice()
        }
        return Gson().toJson(discountedProducts)
    }
}