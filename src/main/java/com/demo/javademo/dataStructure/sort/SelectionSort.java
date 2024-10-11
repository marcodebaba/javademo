package com.demo.javademo.dataStructure.sort;

import java.util.Arrays;

/**
 * @author：marco.pan
 * @ClassName：SelectionSort
 * @Description：选择排序 选择排序算法的实现思路有点类似插入排序，也分已排序区间和未排序区间。
 * 但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
 * @date: 2022年04月10日 12:26 上午
 */
public class SelectionSort {
    // 选择排序，a表示数组，n表示数组大小
    public static void selectionSort(int[] a) {
        if (a.length <= 1) return;

        for (int i = 0; i < a.length - 1; i++) {
            // 查找最小值
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }

            // 交换
            int tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{3, 4, 2, 1, 5, 6, 7, 8};
        selectionSort(array);
        System.out.println(Arrays.toString(array));
    }
}
