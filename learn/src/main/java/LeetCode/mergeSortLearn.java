package LeetCode;

public class mergeSortLearn {
    public static void mergeSort(int[] arr) {
        if (arr.length == 0) return;
        int[] result = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, result);
    }

    // 对 arr 的 [start, end] 区间归并排序
    private static void mergeSort(int[] arr, int start, int end, int[] result) {
        // 只剩下一个数字，停止拆分
        if (start == end) return;
        int middle = (start + end) / 2;
        // 拆分左边区域，并将归并排序的结果保存到 result 的 [start, middle] 区间
        mergeSort(arr, start, middle, result);
        // 拆分右边区域，并将归并排序的结果保存到 result 的 [middle + 1, end] 区间
        mergeSort(arr, middle + 1, end, result);
        // 合并左右区域到 result 的 [start, end] 区间
        merge(arr, start, end, result);
    }

    // 将 result 的 [start, middle] 和 [middle + 1, end] 区间合并
    private static void merge(int[] arr, int start, int end, int[] result) {
        int end1 = (start + end) / 2;
        int start2 = end1 + 1;
        // 用来遍历数组的指针
        int index1 = start;
        int index2 = start2;
        while (index1 <= end1 && index2 <= end) {
            if (arr[index1] <= arr[index2]) {
                result[index1 + index2 - start2] = arr[index1++];
            } else {
                result[index1 + index2 - start2] = arr[index2++];
            }
        }
        // 将剩余数字补到结果数组之后
        while (index1 <= end1) {
            result[index1 + index2 - start2] = arr[index1++];
        }
        while (index2 <= end) {
            result[index1 + index2 - start2] = arr[index2++];
        }
        // 将 result 操作区间的数字拷贝到 arr 数组中，以便下次比较
        while (start <= end) {
            arr[start] = result[start++];
        }
    }
}
