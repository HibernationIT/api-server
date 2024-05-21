package app.hbnationit.apiserver.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    private final ValueOperations<String, String> redisTemplate;


    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate.opsForValue();
    }

    public ValueOperations<String, String> template() {
        return redisTemplate;
    }
}
