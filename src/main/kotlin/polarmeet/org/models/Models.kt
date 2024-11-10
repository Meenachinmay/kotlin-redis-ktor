package polarmeet.org.models

data class Product(
    val id: Int,
    val name: String,
    val price: Double
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)