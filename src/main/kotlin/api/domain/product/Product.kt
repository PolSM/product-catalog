package api.domain.product

import jakarta.persistence.*

@Entity
data class Product(
    @Id
    @Column(unique = true, nullable = false)
    val sku: String,
    val price: Double,
    val description: String,
    @Enumerated(EnumType.STRING)
    val category: Category
)
