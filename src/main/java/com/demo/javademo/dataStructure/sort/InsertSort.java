package com.demo.javademo.dataStructure.sort;

import java.util.Arrays;

/**
 * 当我们需要将一个数据 a 插入到已排序区间时，需要拿 a 与已排序区间的元素依次比较大小，找到合适的插入位置。
 * 找到插入点之后，我们还需要将插入点之后的元素顺序往后移动一位，这样才能腾出位置给元素 a 插入。
 */
public class InsertSort {
    public static void insertionSort(int[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("参数错误");

        for (int i = 1; i < array.length; i++) {
            int insertValue = array[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; j--) {
                if (array[j] > insertValue) {
                    // 数据移动
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            // 插入数据
            array[j + 1] = insertValue;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 8, 6, 3, 9, 2, 1, 7};
        insertionSort(array);
        System.out.println(Arrays.toString(array));
    }
}