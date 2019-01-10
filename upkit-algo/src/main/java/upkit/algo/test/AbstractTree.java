package upkit.algo.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 二叉树的通用类抽象实现
 */
public class AbstractTree implements BinaryTree {

	/**
	 * 返回Class 如果x实现了Comparable接口
	 * 
	 * @param x
	 * @return
	 */
	@Override
	public Class<?> comparableClassFor(Object x) {
		if (x instanceof Comparable) {
			Class<?> c;
			Type[] ts, as;
			Type t;
			ParameterizedType p;
			if ((c = x.getClass()) == String.class) {
				return c;
			}
			// 遍历判断所有接口
			if ((ts = c.getGenericInterfaces()) != null) {
				for (int i = 0; i < ts.length; i++) {
					// 若果该接口为参数类型的接口比如Comparable<T>
					if ((t = ts[i]) instanceof ParameterizedType &&
					// 返回该接口的不带参数的接口对象
							((p = (ParameterizedType) t).getRawType() == Comparable.class &&
							// 返回该接口中的所有真实参数
									(as = p.getActualTypeArguments()) != null && as.length == 1 &&
									// 判断接口参数类型是c的类型
									as[0] == c)) {
						return c;
					}
				}
			}
		}
		return null;
	}

}
