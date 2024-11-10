package polarmeet.org.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import polarmeet.org.models.Product
import polarmeet.org.models.User
import polarmeet.org.services.DataService
import polarmeet.org.services.RedisService
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val redisService = RedisService()
    val dataService = DataService()
    val logger = LoggerFactory.getLogger("Routing")

    routing {
        get("/products") {
            logger.info("Received request for /products")
            val cachedProducts = redisService.get("products", Array<Product>::class.java)?.toList()

            if (cachedProducts != null) {
                logger.info("Returning ${cachedProducts.size} products from cache")
                call.respond(cachedProducts)
            } else {
                logger.info("Cache miss - fetching products from data service")
                val products = dataService.getProducts()
                redisService.set("products", products)
                logger.info("Returning ${products.size} products from data service")
                call.respond(products)
            }
        }

        get("/products/cache") {
            logger.info("Received request to invalidate products cache")
            redisService.delete("products")
            call.respond(HttpStatusCode.OK, mapOf("message" to "Products cache invalidated successfully"))
        }

        get("/users") {
            logger.info("Received request for /users")
            val cachedUsers = redisService.get("users", Array<User>::class.java)?.toList()

            if (cachedUsers != null) {
                logger.info("Returning ${cachedUsers.size} users from cache")
                call.respond(cachedUsers)
            } else {
                logger.info("Cache miss - fetching users from data service")
                val users = dataService.getUsers()
                redisService.set("users", users)
                logger.info("Returning ${users.size} users from data service")
                call.respond(users)
            }
        }
    }
}