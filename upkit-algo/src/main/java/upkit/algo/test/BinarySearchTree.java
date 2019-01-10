package upkit.algo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉搜索树(非平衡)
 *
 * @author melody
 */
public class BinarySearchTree<K, V> extends AbstractTree {

	private static Logger logger = LoggerFactory.getLogger(BinarySearchTree.class);
	// 树根节点
	private Node<K, V> root;

	public BinarySearchTree() {
	}

	public BinarySearchTree(Node<K, V> root) {
		if (comparableClassFor(root.getName()) == null) {
			throw new IllegalArgumentException("构建树的根节点Key的类型未实现Comparable接口。");
		}
		this.root = root;
	}

	/**
	 * 向搜索二叉树中插入节点
	 *
	 * @param node
	 */
	private void insertNode(Node<K, V> node) {
		if (node == null || node.getName() == null) {
			throw new IllegalArgumentException("插入节点的参数不能为空。");
		}

		if (root == null || root.getName() == null) {
			root = node;
			return;
		}
		// 如果树为空
		if (comparableClassFor(node.getName()) == null) {
			throw new IllegalArgumentException("K的类型未实现Comparable接口。");
		}
		Node<K, V> tmp = root;
		// node父节点
		Node<K, V> other = null;
		while (tmp != null) {
			other = tmp;
			if (compareComparables(tmp.getName(), node.getName()) <= 0) {
				tmp = tmp.getRight_child();
			} else {
				tmp = tmp.getLeft_child();
			}
		}
		node.setParent(other);
		if (compareComparables(other.getName(), node.getName()) <= 0) {
			other.setRight_child(node);
		} else {
			other.setLeft_child(node);
		}
	}

	/**
	 * 根据节点名称删除节点
	 *
	 * @param nodeName
	 * @return
	 */
	private Node<K, V> deleteNode(K nodeName) {

		if (nodeName == null || comparableClassFor(nodeName) == null) {
			throw new IllegalArgumentException("非法参数，参数为空或者未实现Comparable接口。");
		}
		if (root == null) {
			throw new RuntimeException("二叉搜索树为空。");
		}

		// 查找当前节点
		Node<K, V> curNode = searchNode(nodeName);
		Node<K, V> parent = curNode.getParent(); // 父节点
		Node<K, V> rcNode = curNode.getRight_child(); // 右子节点
		Node<K, V> lcNode = curNode.getLeft_child(); // 左子节点
		/*
		 * 1. 情形一 当前节点无左子节点和无右子节点 采取方式： 直接删除
		 */
		if (lcNode == null && rcNode == null) {
			Node<K, V> tmp = null;
			// 设置父节点对当前节点的引用为空 并没有清除当前节点
			if ((tmp = parent.getRight_child()) != null && tmp.equals(curNode)) {
				parent.setRight_child(null);
			} else {
				parent.setLeft_child(null);
			}
		}
		/*
		 * 2. 情形二 当前节点有左子节点无右子节点 或者 当前节点无左子节点但有右子节点 采取方式: 删除后
		 * 将父节点的(左或者右)子节点的引用设置为当前节点的(左或者右)子节点
		 */
		if ((lcNode != null && rcNode == null) || (lcNode == null && rcNode != null)) {
			if (rcNode != null) {
				parent.setRight_child(rcNode);
				rcNode.setParent(parent);
			} else {
				parent.setLeft_child(lcNode);
				lcNode.setParent(parent);
			}
		}
		/*
		 * 2. 情形三 当前节点有左子节点和右子节点 采取方式: 直接找到当期节点的右子节点 然后遍
		 * 历该右子节点的左子树，直到遇到第一个为空的左引用。
		 */
		if (lcNode != null && rcNode != null) {
			Node<K, V> tmpNode = rcNode.getLeft_child();
			// 找到当前右子节点下的第一个左子节点引用为空的左节点
			while (tmpNode != null) {
				if (tmpNode.getLeft_child() == null) {
					break;
				}
				tmpNode = tmpNode.getLeft_child();
			}
			// 设置父节点的引用
			if (curNode.equals(parent.getLeft_child())) {
				parent.setLeft_child(rcNode);
			} else {
				parent.setRight_child(rcNode);
			}
			rcNode.setParent(parent);
			if (tmpNode == null) {
				tmpNode = rcNode;
				tmpNode.setLeft_child(lcNode);
			}
			if (tmpNode != null) {
				tmpNode.setLeft_child(lcNode);
			}
			lcNode.setParent(tmpNode);
		}
		return curNode;
	}

	/**
	 * 根据节点名称查询节点
	 *
	 * @param nodeName
	 * @return
	 */
	public Node<K, V> searchNode(K nodeName) {
		if (nodeName == null || comparableClassFor(nodeName) == null) {
			throw new IllegalArgumentException("非法参数，参数为空或者未实现Comparable接口。");
		}
		if (root == null) {
			throw new RuntimeException("二叉搜索树为空。");
		}
		Node<K, V> pNode = null;
		Node<K, V> tmpNode = root;
		while (root != null) {
			pNode = tmpNode;
			if (pNode.getName().equals(nodeName)) {
				return pNode;
			}
			if (compareComparables(tmpNode.getName(), nodeName) <= 0) {
				tmpNode = tmpNode.getRight_child();
			}
			if (compareComparables(tmpNode.getName(), nodeName) > 0) {
				tmpNode = tmpNode.getLeft_child();
			}
		}
		return pNode;
	}

	private int compareComparables(K k, K x) {
		return ((Comparable) k).compareTo((Comparable) x);
	}

	/**
	 * 返回当搜索二叉树的前序遍历节点集合
	 *
	 * @return
	 */
	public List<Node<K, V>> preOrderTraversal() {
		if (root == null) {
			throw new RuntimeException("二叉搜索树为空。");
		}
		List<Node<K, V>> list = new ArrayList<>();
		deepInPreOrderTraversal(list, root);
		return list;
	}

	/**
	 * 前序遍历 （使用递归）
	 *
	 * @param list
	 * @param node
	 */
	private void deepInPreOrderTraversal(List<Node<K, V>> list, Node<K, V> node) {
		if (node == null) {
			return;
		}
		list.add(node);
		deepInPreOrderTraversal(list, node.getLeft_child());
		deepInPreOrderTraversal(list, node.getRight_child());
	}

	/**
	 * 返回当搜索二叉树的中序遍历节点集合
	 *
	 * @return
	 */
	public List<Node<K, V>> inOrderTraversal() {
		if (root == null) {
			throw new RuntimeException("二叉搜索树为空。");
		}
		List<Node<K, V>> list = new ArrayList<>();
		deepInInOrderTraversal(list, root);
		return list;
	}

	/**
	 * 中序遍历 （使用递归）
	 *
	 * @param list
	 * @param node
	 */
	private void deepInInOrderTraversal(List<Node<K, V>> list, Node<K, V> node) {
		if (node == null) {
			return;
		}
		deepInPreOrderTraversal(list, node.getLeft_child());
		list.add(node);
		deepInPreOrderTraversal(list, node.getRight_child());
	}

	/**
	 * 返回当搜索二叉树的后序遍历节点集合
	 *
	 * @return
	 */
	public List<Node<K, V>> postOrderTraversal() {
		if (root == null) {
			throw new RuntimeException("二叉搜索树为空。");
		}
		List<Node<K, V>> list = new ArrayList<>();
		deepInInOrderTraversal(list, root);
		return list;
	}

	/**
	 * 后序遍历 （使用递归）
	 *
	 * @param list
	 * @param node
	 */
	private void deepInPostOrderTraversal(List<Node<K, V>> list, Node<K, V> node) {
		if (node == null) {
			return;
		}
		deepInPostOrderTraversal(list, node.getLeft_child());
		deepInPostOrderTraversal(list, node.getRight_child());
		list.add(node);
	}

	public static void main(String[] args) {

		BinarySearchTree<String, String> binarySearchTree = new BinarySearchTree<>(new Node<>("node11", "data11"));

		Node<String, String> node01 = new Node<>("node01", "data01");
		Node<String, String> node02 = new Node<>("node02", "data02");
		Node<String, String> node03 = new Node<>("node03", "data03");
		Node<String, String> node04 = new Node<>("node04", "data04");
		Node<String, String> node05 = new Node<>("node05", "data05");
		Node<String, String> node12 = new Node<>("node12", "data05");
		Node<String, String> node14 = new Node<>("node14", "data05");
		Node<String, String> node13 = new Node<>("node13", "data05");
		Node<String, String> node16 = new Node<>("node16", "data05");

		binarySearchTree.insertNode(node02);
		binarySearchTree.insertNode(node01);
		binarySearchTree.insertNode(node03);
		binarySearchTree.insertNode(node04);
		binarySearchTree.insertNode(node05);
		binarySearchTree.insertNode(node12);
		binarySearchTree.insertNode(node14);
		binarySearchTree.insertNode(node13);
		binarySearchTree.insertNode(node16);

		List<Node<String, String>> nodes = binarySearchTree.inOrderTraversal();
		for (Node<String, String> node : nodes) {
			System.out.println(node.getName());
		}
	}

}
