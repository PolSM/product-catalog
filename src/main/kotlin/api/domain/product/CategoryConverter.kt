package api.domain.product

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
object CategoryConverter : AttributeConverter<Category, String> {

    override fun convertToDatabaseColumn(category: Category?): String? {
        return category?.value
    }

    override fun convertToEntityAttribute(string: String?): Category? {
        return string?.let {
            Category.entries.find { it.value == string } ?: throw IllegalArgumentException("Unknown category: $string")
        }
    }
}