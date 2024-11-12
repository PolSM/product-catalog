package api.domain.product

import com.google.gson.annotations.SerializedName

enum class Category(val value : String) {
    @SerializedName("Electronics")
    ELECTRONICS("Electronics"),
    @SerializedName("Home & Kitchen")
    HOME_KITCHEN("Home & Kitchen"),
    @SerializedName("Clothing")
    CLOTHING("Clothing"),
    @SerializedName("Accessories")
    ACCESSORIES("Accessories"),
    @SerializedName("Sports")
    SPORTS("Sports"),
    @SerializedName("Musical Instr.")
    MUSICAL_INSTRUMENTS("Musical Instr."),
    @SerializedName("Footwear")
    FOOTWEAR("Footwear"),
    @SerializedName("Home Appliances")
    HOME_APPLIANCES("Home Appliances"),
    @SerializedName("Stationery")
    STATIONERY("Stationery"),
    @SerializedName("Toys & Games")
    TOYS_GAMES("Toys & Games")
}