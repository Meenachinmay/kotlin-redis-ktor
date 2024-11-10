package polarmeet.org.services

import polarmeet.org.models.Product
import polarmeet.org.models.User

class DataService {
    fun getProducts(): List<Product> {
        return listOf(
            Product(1, "Laptop", 999.99),
            Product(2, "Smartphone", 699.99),
            Product(3, "Headphones", 199.99)
        )
    }

    fun getUsers(): List<User> {
        return listOf(
            User(1, "John Doe", "john@example.com"),
            User(2, "Jane Smith", "jane@example.com"),
            User(3, "Bob Johnson", "bob@example.com")
        )
    }
}