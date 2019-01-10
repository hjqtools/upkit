package upkit.algo.test;

/**
 * 最大值，包含从数组的开始到结尾的位置
 *
 * @author imm
 */
public class MaxSum {

    private int left = 0;
    private int right = 0;
    private int sum = 0;

    public MaxSum(int left, int right, int sum) {
        super();
        this.left = left;
        this.right = right;
        this.sum = sum;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
