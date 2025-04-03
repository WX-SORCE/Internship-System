package com.hsbc.pws.common.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisUtils {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	public Set<String> getKeys(String keys) {
		return this.redisTemplate.keys(keys);
	}

	public void setExpire(String key, Object value, int expires) {
		if (expires < 1) {
			expires = 8;
		}

		this.redisTemplate.opsForValue().set(key, value, expires, TimeUnit.HOURS);
	}

	public long getHourTime(String key) {
		return this.redisTemplate.getExpire(key, TimeUnit.HOURS);
	}

	public void setExpireMinutes(String key, Object value, int expires) {
		if (expires < 1) {
			expires = 60;
		}
		this.redisTemplate.opsForValue().set(key, value, expires, TimeUnit.MINUTES);
	}

	public long getMinuteTime(String key) {
		return this.redisTemplate.getExpire(key, TimeUnit.MINUTES);
	}

	public void setExpireSecond(String key, Object value, int expires) {
		if (expires < 1) {
			expires = 60;
		}
		this.redisTemplate.opsForValue().set(key, value, expires, TimeUnit.SECONDS);
	}

	public long getSecondTime(String key) {
		return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	public void deleteTime(String key) {
		this.redisTemplate.boundValueOps(key).persist();
	}

	public void set(String key, Object value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	public Object get(String key) {
		return key == null ? null : this.redisTemplate.opsForValue().get(key);
	}

	public void batchSet(Map<String, String> keyAndValue) {
		this.redisTemplate.opsForValue().multiSet(keyAndValue);
	}

	public void delete(String key) {
		this.redisTemplate.delete(key);
	}

	public void deletes(String keys) {
		this.redisTemplate.delete(this.redisTemplate.keys(keys));
	}

	public void clean() {
		this.redisTemplate.delete(this.redisTemplate.keys("*"));
	}

	public Object hget(String key, String item) {
		return this.redisTemplate.opsForHash().get(key, item);
	}

	public Map<Object, Object> hmget(String key) {
		return this.redisTemplate.opsForHash().entries(key);
	}

	public void hmset(String key, Map<String, Object> map) {
		this.redisTemplate.opsForHash().putAll(key, map);
	}

	public void hmset(String key, Map<String, Object> map, int expires) {
		if (expires < 1) {
			expires = 8;
		}

		this.redisTemplate.expire(key, expires, TimeUnit.HOURS);
	}

	public void lPush(String k, Object v) {
		this.redisTemplate.opsForList().rightPush(k, v);
	}

	/**
	 * 列表获取
	 *
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String k, long l, long l1) {
		return this.redisTemplate.opsForList().range(k, l, l1);
	}

	/**
	 * 集合添加
	 *
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		this.redisTemplate.opsForSet().add(key, value);
	}

	public void randomMembers(String key, long count) {
		this.redisTemplate.opsForSet().randomMembers(key, count);
	}

	/**
	 * 随机获取变量中的元素
	 *
	 * @param key �?
	 * @return
	 */
	public Object randomMember(String key) {
		return this.redisTemplate.opsForSet().randomMember(key);
	}

	/**
	 * 弹出变量中的元素
	 *
	 * @param key �?
	 * @return
	 */
	public Object pop(String key) {
		return this.redisTemplate.opsForSet().pop("setValue");
	}

	/**
	 * 获取变量中值的长度
	 *
	 * @param key �?
	 * @return
	 */
	public long size(String key) {
		return this.redisTemplate.opsForSet().size(key);
	}

	/**
	 * 根据value从一个set中查�?,是否存在
	 *
	 * @param key   �?
	 * @param value �?
	 * @return true 存在 false不存�?
	 */
	public boolean sHasKey(String key, Object value) {
		return this.redisTemplate.opsForSet().isMember(key, value);
	}

	/**
	 * 检查给定的元素是否在变量中�?
	 *
	 * @param key �?
	 * @param obj 元素对象
	 * @return
	 */
	public boolean isMember(String key, Object obj) {
		return this.redisTemplate.opsForSet().isMember(key, obj);
	}

	/**
	 * 转移变量的元素值到目的变量�?
	 *
	 * @param key     �?
	 * @param value   元素对象
	 * @param destKey 元素对象
	 * @return
	 */
	public boolean move(String key, String value, String destKey) {
		return this.redisTemplate.opsForSet().move(key, value, destKey);
	}

	/**
	 * 批量移除set缓存中元�?
	 *
	 * @param key    �?
	 * @param values �?
	 * @return
	 */
	public void remove(String key, Object... values) {
		this.redisTemplate.opsForSet().remove(key, values);
	}

	/**
	 * 通过给定的key�?2个set变量的差�?
	 *
	 * @param key     �?
	 * @param destKey �?
	 * @return
	 */
	public Set<Object> difference(String key, String destKey) {
		return this.redisTemplate.opsForSet().difference(key, destKey);
	}

	/**
	 * 集合获取
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		return this.redisTemplate.opsForSet().members(key);
	}

	/**
	 * 有序集合添加
	 *
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) {
		this.redisTemplate.opsForZSet().add(key, value, scoure);
	}

	/**
	 * 有序集合获取
	 *
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		return this.redisTemplate.opsForZSet().rangeByScore(key, scoure, scoure1);
	}

	/**
	 * 加入缓存
	 *
	 * @param key �?
	 * @param map �?
	 * @return
	 */
	public void add(String key, Map<String, String> map) {
		this.redisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * 获取 key 下的 所�? hashkey �? value
	 *
	 * @param key �?
	 * @return
	 */
	public Map<Object, Object> getHashEntries(String key) {
		return this.redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 验证指定 key �? 有没有指定的 hashkey
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hashKey(String key, String hashKey) {
		return this.redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * 获取指定key的值string
	 *
	 * @param key  �?
	 * @param key2 �?
	 * @return
	 */
	public String getMapString(String key, String key2) {
		return this.redisTemplate.opsForHash().get("map1", "key1").toString();
	}

	/**
	 * 获取指定的值Int
	 *
	 * @param key  �?
	 * @param key2 �?
	 * @return
	 */
	public Integer getMapInt(String key, String key2) {
		return (Integer) this.redisTemplate.opsForHash().get("map1", "key1");
	}

	/**
	 * 弹出元素并删�?
	 *
	 * @param key �?
	 * @return
	 */
	public String popValue(String key) {
		return this.redisTemplate.opsForSet().pop(key).toString();
	}

	/**
	 * 删除指定 hash �? HashKey
	 *
	 * @param key
	 * @param hashKeys
	 * @return 删除成功�? 数量
	 */
	public void delete(String key, String... hashKeys) {
		this.redisTemplate.opsForHash().delete(key, hashKeys);
	}

	/**
	 * 给指�? hash �? hashkey 做增减操�?
	 *
	 * @param key
	 * @param hashKey
	 * @param number
	 * @return
	 */
	public Long increment(String key, String hashKey, long number) {
		return this.redisTemplate.opsForHash().increment(key, hashKey, number);
	}

	/**
	 * 给指�? hash �? hashkey 做增减操�?
	 *
	 * @param key
	 * @param hashKey
	 * @param number
	 * @return
	 */
	public Double increment(String key, String hashKey, Double number) {
		return this.redisTemplate.opsForHash().increment(key, hashKey, number);
	}

	/**
	 * 获取 key 下的 所�? hashkey 字段
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> hashKeys(String key) {
		return this.redisTemplate.opsForHash().keys(key);
	}

	/**
	 * 获取指定 hash 下面�? 键值对 数量
	 *
	 * @param key
	 * @return
	 */
	public Long hashSize(String key) {
		return this.redisTemplate.opsForHash().size(key);
	}

	/**
	 * 在变量左边添加元素�?
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public void leftPush(String key, Object value) {
		this.redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 获取集合指定位置的值�?
	 *
	 * @param key
	 * @param index
	 * @return
	 */
	public Object index(String key, long index) {
		return this.redisTemplate.opsForList().index("list", 1);
	}

	/**
	 * 获取指定区间的值�?
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> range(String key, long start, long end) {
		return this.redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 把最后一个参数值放到指定集合的第一个出现中间参数的前面�? 如果中间参数值存在的话�?
	 *
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public void leftPush(String key, String pivot, String value) {
		this.redisTemplate.opsForList().leftPush(key, pivot, value);
	}

	/**
	 * 向左边批量添加参数元素�?
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public void leftPushAll(String key, String... values) {
//        redisTemplate.opsForList().leftPushAll(key,"w","x","y");
		this.redisTemplate.opsForList().leftPushAll(key, values);
	}

	/**
	 * 向集合最右边添加元素�?
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public void leftPushAll(String key, String value) {
		this.redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 向左边批量添加参数元素�?
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public void rightPushAll(String key, String... values) {
		// redisTemplate.opsForList().leftPushAll(key,"w","x","y");
		this.redisTemplate.opsForList().rightPushAll(key, values);
	}

	/**
	 * 向已存在的集合中添加元素�?
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public void rightPushIfPresent(String key, Object value) {
		this.redisTemplate.opsForList().rightPushIfPresent(key, value);
	}

	/**
	 * 向已存在的集合中添加元素�?
	 *
	 * @param key
	 * @return
	 */
	public long listLength(String key) {
		return this.redisTemplate.opsForList().size(key);
	}

	/**
	 * 移除集合中的左边第一个元素�?
	 *
	 * @param key
	 * @return
	 */
	public void leftPop(String key) {
		this.redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出�?
	 *
	 * @param key
	 * @return
	 */
	public void leftPop(String key, long timeout, TimeUnit unit) {
		this.redisTemplate.opsForList().leftPop(key, timeout, unit);
	}

	/**
	 * 移除集合中右边的元素�?
	 *
	 * @param key
	 * @return
	 */
	public void rightPop(String key) {
		this.redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出�?
	 *
	 * @param key
	 * @return
	 */
	public void rightPop(String key, long timeout, TimeUnit unit) {
		this.redisTemplate.opsForList().rightPop(key, timeout, unit);
	}

	/**
	 * 加锁，无阻塞
	 *
	 * @param
	 * @param
	 * @return
	 */
	public Boolean tryLock(String key, String reqid, int expireTime, int timeout) {
		Long start = System.currentTimeMillis();
		try {
			for (;;) {
				// SET命令返回OK ，则证明获取锁成�?
				Boolean ret = this.redisTemplate.opsForValue().setIfAbsent(key, reqid, expireTime, TimeUnit.SECONDS);
				if (ret) {
					return true;
				}
				// 否则循环等待，在timeout时间内仍未获取到锁，则获取失�?
				long end = System.currentTimeMillis() - start;
				if (end >= timeout) {
					return false;
				}
			}
		} finally {

		}

	}

	/**
	 * 加锁
	 * 
	 * @param key        锁的key
	 * @param reqid      请求id
	 * @param expireTime 加锁有效时间 （分钟）
	 * @return
	 */
	public boolean tryLock(String key, String reqid, int expireTime) {
		Boolean ret = this.redisTemplate.opsForValue().setIfAbsent(key, reqid, expireTime, TimeUnit.SECONDS);
		if (ret) {
			return true;
		}
		return false;
	}

	/**
	 * 解锁
	 * 
	 * @param key   锁的key
	 * @param reqid
	 * @return
	 */
	public Boolean unlock(String key, String reqid) {
		if (this.redisTemplate.opsForValue().get(key).equals(reqid)) {
			return this.redisTemplate.delete(key);
		}
		return false;
	}

	/**
	 * 计数器（步长�?1�?
	 * 
	 * @param key     key�?
	 * @param expires 有效期（小时�?
	 * @return
	 */
	public long incrExpireBykey(String key, int expires) {
		return incrExpireBykey(key, 1, expires);
	}

	/**
	 * 计数�?
	 * 
	 * @param key     key�?
	 * @param delta   增量步长
	 * @param expires 有效期（小时�?
	 * @return
	 */
	public long incrExpireBykey(String key, long delta) {
		this.redisTemplate.opsForValue().setIfAbsent(key, 0);
		return this.redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 计数�?
	 * 
	 * @param key     key�?
	 * @param delta   增量步长
	 * @param expires 有效期（小时�?
	 * @return
	 */
	public long incrExpireBykey(String key, long delta, int expires) {
		this.redisTemplate.opsForValue().setIfAbsent(key, 0, expires, TimeUnit.HOURS);
		return this.redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 计数�?
	 * 
	 * @param key key�?
	 * @return
	 */
	public long incrBykey(String key) {
		return this.redisTemplate.opsForValue().increment(key, 1);
	}

}