package api.application

import api.domain.product.Category
import api.infrastructure.JpaProductRepository
import com.google.gson.Gson
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: JpaProductRepository
) {
    fun getProducts(category: Category?, sortBy: Sort): String {
        val products = productRepository.findAll(category, sortBy)

        val discountedProducts = products.map { product ->
            product.calculateDiscountedPrice()
        }
        return Gson().toJson(discountedProducts)
    }
}