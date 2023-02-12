package com.demo.javademo.dataStructure.sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 快速排序思想是基于分治策略，算法思想如下：
 * 1. 分解：从数列中选出一个元素作为基准元素。以基准元素为基础，将问题分解为两个子序列，使小于等于基准元素的子序列在左侧，大于基准元素的子序列在右侧
 * 2. 治理：对两个子序列进行快速排序
 * 3. 合并：将排好序的两个子序列合并，得到原问题的解
 *
 *
 * 快排的思想是这样的：如果要排序数组中下标从 p 到 r 之间的一组数据，我们选择 p 到 r 之间的任意一个数据作为 pivot（分区点）。
 * 我们遍历 p 到 r 之间的数据，将小于 pivot 的放到左边，将大于 pivot 的放到右边，将 pivot 放到中间。经过这一步骤之后，
 * 数组 p 到 r 之间的数据就被分成了三个部分，前面 p 到 q-1 之间都是小于 pivot 的，中间是 pivot，后面的 q+1 到 r 之间是大于 pivot 的。
 */
public class QuickSort {
    /**
     * 递归算法
     * @param array
     * @param low
     * @param high
     */
//    public static void quickSort(int[] array, int low, int high) {
//        if (array == null)
//            throw new IllegalArgumentException("参数错误");
//        if (low >= high) return;
//
//        int pivotIndex = partition(array, low, high);
//        quickSort(array, low, pivotIndex - 1);
//        quickSort(array, pivotIndex + 1, high);
//    }

    /**
     * 非递归算法
     *
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int q = partition(arr, left, right);
        quickSort(arr, left, q - 1);
        quickSort(arr, q + 1, right);
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = arr[i];
                    arr[i++] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        int tmp = arr[i];
        arr[i] = arr[right];
        arr[right] = tmp;
        return i;
    }

    /**
     * 随机选择一个元素作为 pivot（一般情况下，可以选择 p 到 r 区间的最后一个元素），然后对 A[p...r]分区，函数返回 pivot 的下标。
     * @param array
     * @param low
     * @param high
     * @return
     */
    public static int partition2(int[] array, int low, int high) {
        int i = low, j = high, pivot = array[low];
        while (i < j) {
            while (i < j && array[j] > pivot) j--;
            while (i < j && array[i] <= pivot) i++;
            if (i < j) {
                // array[i]和array[j]互换
                int temp = array[i];
                array[i++] = array[j];
                array[j--] = temp;
            }
        }
        // i = j，如果array[i] > pivot，则array[i - 1] 与 pivot 互换
        if (array[i] > pivot) {
            int temp = array[i - 1];
            array[i - 1] = array[low];
            array[low] = temp;
            return i - 1;
        }
        // 否则array[i] 与 pivot 互换
        int temp = array[i];
        array[i] = array[low];
        array[low] = temp;
        return i;
    }

    public static void main(String[] args) {
        int[] array = new int[]{4, 4, 6, 5, 3, 2, 8, 1};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
