package api.domain.product

import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun findAll(category: Category?, pageable: Pageable): List<Product>
}