package com.shoppingService.LeetCode.Arrays.Problem_75;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortColors {
    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    private static int partition(int low, int high, int[] array) {
        int pnt = low - 1;
        for (int a = low; a <= high; a++) {
            if (array[a] < array[high]) {
                ++pnt;
                swap(pnt, a, array);
            }
        }
        swap(pnt + 1, high, array);
        return ++pnt;
    }

    private static void quickSort(int low, int high, int[] array) {
        if (low < high) {
            int pivotIndex = partition(low, high, array);
            quickSort(low, pivotIndex - 1, array);
            quickSort(pivotIndex + 1, high, array);
        }
    }

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{2, 0, 2, 1, 1, 0});
        list.add(new int[]{2, 0, 1});
        for (int[] nums : list) {
            String originalArray = Arrays.toString(nums);
            quickSort(0, nums.length - 1, nums);
            System.out.println("Array: " + originalArray + " Sorted Array: " + Arrays.toString(nums));
        }
    }
}
