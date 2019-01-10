package upkit.pool;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:  演示连接池
 * @author: melody
 * @email:  cuzart@163.com
 */
public class DemoPool {

	private static final Logger logger = LoggerFactory.getLogger(DemoPool.class);
	public static void main(String[] args) {
		
//		String docker = "玩的贼溜";
//		System.out.println(docker);
		
		File file = new File("logs");
		logger.info("dsfsdfdf");
		System.out.println(file.getAbsolutePath());
		String x = file.getAbsolutePath() +"/sd";
		File dd = new File(x);
		dd.mkdir();
	}
	
}
