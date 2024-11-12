package api.infrastructure

import api.domain.product.Product
import api.domain.product.ProductRepository
import api.domain.product.Category
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JpaProductRepository : ProductRepository, JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category = :category)")
    override fun findAll(@Param("category") category: Category?, sort: Sort): List<Product>
}