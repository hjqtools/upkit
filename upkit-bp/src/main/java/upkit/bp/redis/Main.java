package upkit.bp.redis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Main {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//		RedisDao bean = (RedisDao) context.getBean("redisDao");
		RedisTemplate bean = context.getBean(RedisTemplate.class);
		System.out.println(bean);
	}

}
