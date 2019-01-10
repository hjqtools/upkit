package upkit.algo.test;

/**
 * 分治算法，求数组中最大的顺序元素集合
 * <p>
 * 最大值数组必然存在于以下三种情况
 * <p>
 * <p>
 * 完全位于子数组A[low..mid]中 完全位于子数组A[mid+1..high]中 位于mid的的两边
 *
 * @author melody
 */
public class DivideAndConquer {

    // 获取最大值
    public static MaxSum findMaxSumSubArray(int[] a, int low, int high) {

        int mid = 0; // 数组中的中间值

        MaxSum leftSum = null; // 记录左边的最大值
        MaxSum rightSum = null; // 记录右边的最大值
        MaxSum crossSum = null; // 记录中间的最大值

        if (high == low) {
            return new MaxSum(low, high, a[low]); // 只有一个元素
        } else {
            mid = (low + high) / 2;
            leftSum = findMaxSumSubArray(a, low, mid);
            rightSum = findMaxSumSubArray(a, mid + 1, high);
            crossSum = findMaxCrosingSubArray(a, low, mid, high);
        }

        if (leftSum.getSum() >= rightSum.getSum() && leftSum.getSum() >= crossSum.getSum())
            return new MaxSum(leftSum.getLeft(), leftSum.getRight(), leftSum.getSum());
        else if (rightSum.getSum() >= leftSum.getSum() && rightSum.getSum() >= crossSum.getSum())
            return new MaxSum(rightSum.getLeft(), rightSum.getRight(), rightSum.getSum());
        else
            return new MaxSum(crossSum.getLeft(), crossSum.getRight(), crossSum.getSum());
    }

    /**
     * 发现中路的最大值s
     *
     * @param a
     * @param low
     * @param mid
     * @param high
     * @return
     */
    private static MaxSum findMaxCrosingSubArray(int[] a, int low, int mid, int high) {

        int leftSum = 0; // 左部分最大值
        int maxLeft = 0; // 从mid到low部分的的最大值的i的位置

        int sum = 0; // 临时变量

        for (int i = mid; i >= low; i--) {
            sum = sum + a[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        sum = 0; // 初始化

        int rightSum = 0; // 右部分的最大值
        int maxRight = 0; // 从mid到high部分取最大值时数组下标的位置

        for (int i = mid + 1; i <= high; i++) {
            sum = sum + a[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }
        // 返回封装好的最大值以及数组的下标
        return new MaxSum(maxLeft, maxRight, leftSum + rightSum);
    }

}
