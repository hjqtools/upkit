package upkit.utils.bean;

/**
 * 
 * @author melody
 *
 * @param <T>
 * @param <K>
 */
public class Tuple<T, K> {

	private T t;
	private K k;

	public Tuple(T t, K k) {
		super();
		this.t = t;
		this.k = k;
	}

	public static <T, K> Tuple<T, K> build(T first, K second) {
		return new Tuple<T, K>(first, second);
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public K getK() {
		return k;
	}

	public void setK(K k) {
		this.k = k;
	}

}
