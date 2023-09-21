package com.example.demo.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory1) {
        StringRedisTemplate redisTemplate1 = new StringRedisTemplate();
        redisTemplate1.setConnectionFactory(connectionFactory1);
        return redisTemplate1;
    }

    @Bean
    public StringRedisTemplate redisTemplate2(RedisConnectionFactory connectionFactory2) {
        StringRedisTemplate redisTemplate2 = new StringRedisTemplate();
        redisTemplate2.setConnectionFactory(connectionFactory2);
        return redisTemplate2;
    }

    @Bean
    public RedisConnectionFactory connectionFactory1() {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName(env.getProperty("spring.redis1.host"));
        connectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis1.port")));
        connectionFactory.setPassword(env.getProperty("spring.redis1.password"));
        return connectionFactory;
    }

    @Bean
    public RedisConnectionFactory connectionFactory2() {
        LettuceConnectionFactory connectionFactory2 = new LettuceConnectionFactory();
        connectionFactory2.setHostName(env.getProperty("spring.redis2.host"));
        connectionFactory2.setPort(Integer.parseInt(env.getProperty("spring.redis2.port")));
        connectionFactory2.setPassword(env.getProperty("spring.redis2.password"));
        return connectionFactory2;
    }

//    @Bean
//    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.afterPropertiesSet();
//        return template;
//    }

}