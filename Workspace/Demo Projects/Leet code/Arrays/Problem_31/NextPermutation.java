package com.shoppingService.LeetCode.Arrays.Problem_31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextPermutation {
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
            nextPermutation(nums);
            System.out.println(Arrays.toString(nums));
        }
    }

    public static void nextPermutation(int[] nums) {
        int pnt = -1;
        for (int a = nums.length - 2; a > -1; a--) {
            if (nums[a] < nums[a + 1]) {
                pnt = a;
                break;
            }
        }
        if (pnt < 0) {
            reverseArray(0, nums.length - 1, nums);
        } else {
            int pnt2 = nums.length - 1;
            while (nums[pnt2] <= nums[pnt]) --pnt2;
            swap(pnt, pnt2, nums);
            reverseArray(pnt + 1, nums.length - 1, nums);
        }
    }

    private static void swap(int num1Index, int num2Index, int[] array) {
        int temp = array[num1Index];
        array[num1Index] = array[num2Index];
        array[num2Index] = temp;
    }

    private static void reverseArray(int leftPnt, int rightPnt, int[] array) {
        while (leftPnt < rightPnt) {
            swap(leftPnt, rightPnt, array);
            ++leftPnt;
            --rightPnt;
        }
    }
}
