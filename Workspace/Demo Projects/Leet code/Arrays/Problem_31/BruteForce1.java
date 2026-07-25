package com.shoppingService.LeetCode.Arrays.Problem_31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce1 {
    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2, 3});
        list.add(new int[]{1, 3, 2});
        list.add(new int[]{2, 1, 3});
        list.add(new int[]{2, 3, 1});
        list.add(new int[]{3, 1, 2});
        list.add(new int[]{3, 2, 1});
        list.add(new int[]{1, 1, 5});
        list.add(new int[]{1, 3, 5, 4, 2});
        list.add(new int[]{5, 4, 3, 2, 1});
        list.add(new int[]{2, 1, 5, 4});
        for (int[] nums : list) {
            System.out.println();
            System.out.println(Arrays.toString(nums));
            validateArray(nums);
            System.out.println(Arrays.toString(nums));
        }
    }

    private static void validateArray(int[] nums) {
        if (nums.length < 1 || nums.length > 100) {
            System.out.println("Invalid array.");
            return;
        }
        for (int a = 0; a < nums.length; a++) {
            if (nums[a] < 0 || nums[a] > 100) {
                System.out.println("Invalid inputs.");
                return;
            }
        }
        permutation(nums);
    }

    private static void permutation(int[] array) {
        int pnt = -1;
        for (int a = array.length - 2; a > -1; a--) {
            if (array[a] < array[a + 1]) {
                pnt = a;
                break;
            }
        }
        if (pnt < 0) {
            quickSort(0, array.length - 1, array);
        } else {
            int pnt2 = array.length - 1;
            for (int a = pnt2; a > pnt; a--) {
                if (array[a] > array[pnt]) break;
                --pnt2;
            }
            swap(pnt, pnt2, array);
            quickSort(pnt + 1, array.length - 1, array);
        }
    }

    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    private static void quickSort(int low, int high, int[] array) {
        if (low < high) {
            int pivotIndex = partition(low, high, array);
            quickSort(low, pivotIndex - 1, array);
            quickSort(pivotIndex + 1, high, array);
        }
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
}
