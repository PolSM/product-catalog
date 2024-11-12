package api.application

import api.domain.product.Category
import api.domain.product.ProductRepository
import org.springframework.stereotype.Service
import com.google.gson.Gson
import org.springframework.data.domain.Sort

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProducts(category: Category?, sortBy: Sort?): String {
        val products = productRepository.findAll(category, sortBy)

        val discountedProducts = products.map { product ->
            product.calculateDiscountedPrice()
        }
        return Gson().toJson(discountedProducts)
    }
}