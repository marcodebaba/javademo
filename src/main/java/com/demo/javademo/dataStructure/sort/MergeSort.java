package com.demo.javademo.dataStructure.sort;

import java.util.Arrays;

/**
 * 时间复杂度O(nlogn)，空间复杂度O(n平方)
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {4, 4, 6, 5, 3, 2, 8, 1};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void mergeSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            //左边归并排序，使得左子序列有序
            mergeSort(arr, low, mid);
            //右边归并排序，使得右子序列有序
            mergeSort(arr, mid + 1, high);
            //将两个有序子数组合并操作
            merge(arr, low, mid, high);
        }
    }

    // 把两个有序的数组merge成一个有序数组
    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;
        while (i <= mid && j <= high) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= high) temp[k++] = arr[j++];
        k = 0;
        //将temp中的元素全部拷贝到原数组中
        while (low <= high) {
            arr[low++] = temp[k++];
        }
    }
}
