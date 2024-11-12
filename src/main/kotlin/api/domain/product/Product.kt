package api.domain.product

import jakarta.persistence.*

@Entity
@Table(name = "tbl_products")
data class Product(
    @Id
    @Column(unique = true, nullable = false)
    val sku: String,
    val price: Double,
    val description: String,
    val category: Category
) {
    fun calculateDiscountedPrice(): Product {
        val discount = when {
            sku.endsWith("5") -> 0.30
            category == Category.HOME_KITCHEN -> 0.25
            category == Category.ELECTRONICS -> 0.15
            else -> 0.0
        }
        val discountedPrice = if (discount > 0) price * (1 - discount) else return this
        return this.copy(price = discountedPrice)
    }
}
