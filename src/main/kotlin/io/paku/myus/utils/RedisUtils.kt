package io.paku.myus.utils

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisUtils(
    private val stringRedisTemplate: StringRedisTemplate
) {

    private val valueOperations = stringRedisTemplate.opsForValue()

    fun getData(key: String): String {
        return valueOperations[key] ?: ""
    }

    fun setData(key: String, value: String) {
        valueOperations.set(key, value)
    }

    fun setDataWithExpiration(key: String, value: String, duration: Long) {
        val expireDuration = Duration.ofSeconds(duration)
        valueOperations.set(key, value, expireDuration)
    }

    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }
}