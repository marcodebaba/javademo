package com.demo.javademo.dataStructure.sort;

import java.util.Arrays;

/**
 * @author：marco.pan
 * @ClassName：ShellSort
 * @Description：
 * @date: 2022年04月18日 11:58
 */
public class ShellSort {
    public static void shellSort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int step = length / 2; step >= 1; step /= 2) {
            for (int i = step; i < length; i++) {
                temp = arr[i];
                int j = i - step;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + step] = arr[j];
                    j -= step;
                }
                arr[j + step] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{3, 4, 2, 1, 5, 6, 7, 8};
        shellSort(array);
        System.out.println(Arrays.toString(array));
    }
}
