package upkit.algo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 红黑树 实现
 * 
 * @author melody
 * 
 *         1）每个结点要么是红的，要么是黑的。 
 *         2）根结点是黑的。
 *         3）每个叶结点（叶结点即指树尾端NIL指针或NULL结点）是黑的。
 *         4）如果一个结点是红的，那么它的俩个儿子都是黑的。
 *         5）对于任一结点而言，其到叶结点树尾端NIL指针的每一条路径都包含相同数目的黑结点。
 *
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K, V> extends AbstractTree {

	private static Logger logger = LoggerFactory.getLogger(BinarySearchTree.class);
	// NIL节点，所有指向为空的指针指向的节点
	private RBNode<K, V> NIL = new RBNode<K, V>(null, null, Color.BLACK);
	// 树根节点
	private RBNode<K, V> root;

	public RedBlackTree(RBNode<K, V> root) {
		if (comparableClassFor(root.getName()) == null) {
			throw new IllegalArgumentException("构建树的根节点Key的类型未实现Comparable接口。");
		}
		this.root = root;
	}

	/**
	 * 插入节点
	 * 
	 * @param rbNode
	 */
	private void insert(RBNode<K, V> rbNode) {
		if (rbNode == null || rbNode.getName() == null) {
			throw new IllegalArgumentException("插入节点的参数不能为空。");
		}
		if (root == null || root.getName() == null) {
			rbNode.setColor(Color.BLACK);
			root = rbNode;
			return;
		}

		// 如果树为空
		if (comparableClassFor(rbNode.getName()) == null) {
			throw new IllegalArgumentException("K的类型未实现Comparable接口。");
		}
		RBNode<K, V> tmp = root;
		// node父节点
		RBNode<K, V> other = null;
		while (tmp != null) {
			other = tmp;
			if (compareComparables(tmp.getName(), rbNode.getName()) <= 0) {
				tmp = tmp.getRight_child();
			} else {
				tmp = tmp.getLeft_child();
			}
		}
		rbNode.setParent(other);
		if (compareComparables(other.getName(), rbNode.getName()) <= 0) {
			other.setRight_child(rbNode);
		} else {
			other.setLeft_child(rbNode);
		}
		/*
		 * 二叉搜索树不同的关键部分
		 */
		// 设置节点属性
		rbNode.setLeft_child(NIL);
		rbNode.setRight_child(NIL);
		rbNode.setColor(Color.RED);

	}
	
	/**
	 * 插入修复
	 * @param rbNode
	 */
	private void rbInsertFixup(RBNode<K, V> rbNode) {
		RBNode<K, V> np = rbNode.getParent();
		RBNode<K,V> tmp = null;
		while(np.getColor() == Color.RED) {
			if(np == np.getParent().getLeft_child()) {
				tmp = np.getParent().getRight_child();
			}
			if(tmp.getColor() == Color.RED) {
				np.setColor(Color.BLACK);
			}
		}
	}

	private int compareComparables(K k, K x) {
		return ((Comparable) k).compareTo((Comparable) x);
	}

	/**
	 * 红黑树节点
	 * 
	 * @author melody
	 *
	 * @param <K>
	 * @param <V>
	 */
	class RBNode<K, V> {
		// 父节点
		private RBNode<K, V> parent;
		// 左子节点
		private RBNode<K, V> left_child;
		// 右子节点
		private RBNode<K, V> right_child;
		// 节点名称
		private K name;
		// 数据
		private V data;
		// 节点颜色
		private Color color;

		public RBNode(K name, V data) {
			this.name = name;
			this.data = data;
			this.color = Color.BLACK;
		}

		public RBNode(K name, V data, Color color) {
			this.name = name;
			this.data = data;
			this.color = color;
		}

		public RBNode<K, V> getParent() {
			return parent;
		}

		public void setParent(RBNode<K, V> parent) {
			this.parent = parent;
		}

		public RBNode<K, V> getLeft_child() {
			return left_child;
		}

		public void setLeft_child(RBNode<K, V> left_child) {
			this.left_child = left_child;
		}

		public RBNode<K, V> getRight_child() {
			return right_child;
		}

		public void setRight_child(RBNode<K, V> right_child) {
			this.right_child = right_child;
		}

		public K getName() {
			return name;
		}

		public void setName(K name) {
			this.name = name;
		}

		public V getData() {
			return data;
		}

		public void setData(V data) {
			this.data = data;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

	}

	enum Color {
		RED, BLACK
	}

}
