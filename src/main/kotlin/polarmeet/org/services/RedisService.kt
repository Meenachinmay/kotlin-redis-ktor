package polarmeet.org.services

import redis.clients.jedis.JedisPool
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory

class RedisService {
    private val jedisPool = JedisPool("localhost", 6379)
    private val mapper = jacksonObjectMapper()
    private val logger = LoggerFactory.getLogger(RedisService::class.java)

    fun <T : Any> get(key: String, clazz: Class<T>): T? {
        return jedisPool.resource.use { jedis ->
            val value = jedis.get(key)
            if (value != null) {
                mapper.readValue(value, clazz)
            } else {
                null
            }
        }
    }

    fun <T> set(key: String, value: T) {
        jedisPool.resource.use { jedis ->
            jedis.set(key, mapper.writeValueAsString(value))
        }
    }

    fun delete(key: String) {
        jedisPool.resource.use { jedis ->
            logger.info("Deleting cache for key: $key")
            jedis.del(key)
        }
    }
}