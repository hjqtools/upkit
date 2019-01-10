package upkit.algo.test;
/**
 * 二叉树节点
 *
 * @param <K>
 */
public class Node<K, V> {

    // 父节点
    private Node<K, V> parent;
    // 左子节点
    private Node<K, V> left_child;
    // 右子节点
    private Node<K, V> right_child;
    // 节点名称
    private K name;
    // 数据
    private V data;
    // 

    public Node(K name, V data) {
        this.name = name;
        this.data = data;
    }

    public K getName() {
        return name;
    }

    public void setName(K name) {
        this.name = name;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    public Node<K, V> getLeft_child() {
        return left_child;
    }

    public void setLeft_child(Node<K, V> left_child) {
        this.left_child = left_child;
    }

    public Node<K, V> getRight_child() {
        return right_child;
    }

    public void setRight_child(Node<K, V> right_child) {
        this.right_child = right_child;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }


}
