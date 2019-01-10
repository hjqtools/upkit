package upkit.bp.redis;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valOps;

	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOps;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOps;

	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> zsetOps;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Set<String> getZSetPaged(String key, long start, long end) {
		return zsetOps.range(key, start, end);
	}

	@Override
	public boolean zsetAdd(String key, String value, double score) {
		return zsetOps.add(key, value, score);
	}

	@Override
	public Long zsetSize(String key) {
		return zsetOps.size(key);
	}

	@Override
	public void del(String key) {
		valOps.getOperations().delete(key);
	}

	@Override
	public boolean hasKey(String key) {
		return valOps.getOperations().hasKey(key);
	}

	@Override
	public Long incr(String key) {
		Long increment = valOps.increment(key, 1L);
		if (increment == 1 || this.redisTemplate.getExpire(key) == null || this.redisTemplate.getExpire(key) == -1) {
			LocalDateTime localDateTime = LocalDate.now().plusDays(1).atStartOfDay();
			ZoneId zone = ZoneId.systemDefault();
			Instant instant = localDateTime.atZone(zone).toInstant();
			redisTemplate.expireAt(key, Date.from(instant));
		}
		return increment;
	}

	@Override
	public void set(String key, String value, long expired) {
		valOps.set(key, value, expired, TimeUnit.SECONDS);
	}

	@Override
	public void set(String key, String value) {
		valOps.set(key, value);
	}

	@Override
	public String get(String key) {
		return valOps.get(key);
	}

	@Override
	public Set<String> getKeys(String pattern) {
		return valOps.getOperations().keys(pattern);
	}

	@Override
	public Long getIncr(String key) {
		Object value = valOps.get(key);
		if (value == null) {
			return 0L;
		}
		return Long.valueOf(valOps.get(key).toString());
	}

	@Override
	public void setAdd(String key, String value) {
		setOps.add(key, value);
	}

	@Override
	public boolean isSetMember(String key, String value) {
		return setOps.isMember(key, value);
	}

	@Override
	public long setRemove(String key, String value) {
		return setOps.remove(key, value);
	}

	@Override
	public Long setSize(String key) {
		return setOps.size(key);
	}

	public void hashAdd(String key, String hashKey, String value) {
		hashOps.put(key, hashKey, value);
	}

	public void hashRemove(String key, String hashKey) {
		hashOps.delete(key, hashKey);
	}

	public boolean isHashKey(String key, String hashKey) {
		return hashOps.hasKey(key, hashKey);
	}

	public String getHashValue(String key, String hashKey) {
		return hashOps.get(key, hashKey);
	}

	@Override
	public Long hashSize(String key) {
		return hashOps.size(key);
	}

	@Override
	public void leftPush(String key, String value) {
		listOps.leftPush(key, value);
	}

	@Override
	public String rightPop(String key) {
		return listOps.rightPop(key);
	}

	@Override
	public Long listSize(String key) {
		return listOps.size(key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set getMembers(String key) {
		return setOps.members(key);
	}
}
