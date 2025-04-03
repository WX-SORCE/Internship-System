package com.hsbc.pws.common.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LocalCacheUtils {
	@Value("${local.cache.expire:5}")
	private int expire;
	private final RedisUtils redisUtil;

	private static Cache<String, Object> CACHE;

	private static LocalCacheUtils INSTANCE;

	@PostConstruct
	public void init() throws Exception {
		CACHE = Caffeine.newBuilder().maximumSize(20000).softValues().expireAfterWrite(Duration.ofMinutes(expire)).build();
		INSTANCE = this;
	}

	public static Object get(String key) throws Exception {
		return CACHE.get(key, INSTANCE.redisUtil::get);
	}

	public static Object get(String key, Function<String, ?> function) throws Exception {
		return CACHE.get(key, function);
	}

	public static void invalidate(String key) throws Exception {
		CACHE.invalidate(key);
	}
}
