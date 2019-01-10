package upkit.bp.redis;

import java.util.Set;

public interface RedisDao {

	void del(String key);

	boolean hasKey(String key);

	Long incr(String key);

	/**
	 * 
	 * @param key
	 * @param value
	 * @param expired
	 *            TimeUnit.SECONDS
	 */
	void set(String key, String value, long expired);

	void set(String key, String value);

	String get(String key);

	Set<String> getKeys(String pattern);

	Long getIncr(String key);

	void setAdd(String key, String value);

	boolean isSetMember(String key, String value);

	long setRemove(String key, String value);

	Long setSize(String key);

	void hashAdd(String key, String hashKey, String value);

	void hashRemove(String key, String hashKey);

	boolean isHashKey(String key, String hashKey);

	String getHashValue(String key, String hashKey);

	Long hashSize(String key);

	Set<String> getZSetPaged(String key, long start, long end);

	boolean zsetAdd(String key, String value, double score);

	Long zsetSize(String key);

	void leftPush(String key, String value);

	String rightPop(String key);

	Long listSize(String key);

	@SuppressWarnings("rawtypes")
	Set getMembers(String key);
}