package api.domain.product

import org.springframework.data.domain.Sort

interface ProductRepository {
    fun findAll(category: Category?, sort: Sort): List<Product>
}