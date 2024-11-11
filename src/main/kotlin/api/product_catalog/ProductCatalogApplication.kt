package api.product_catalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductCatalogApplication

fun main(args: Array<String>) {
	runApplication<ProductCatalogApplication>(*args)
}
