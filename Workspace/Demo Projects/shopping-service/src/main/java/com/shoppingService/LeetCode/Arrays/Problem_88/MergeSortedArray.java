package com.shoppingService.LeetCode.Arrays.Problem_88;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSortedArray {
    private static List<TestData> prepareTestData() {
        List<TestData> list = new ArrayList<>();
        list.add(new TestData(new int[]{1, 2, 3, 0, 0, 0}, new int[]{2, 5, 6}, 3, 3));
        list.add(new TestData(new int[]{1}, new int[]{}, 0, 1));
        list.add(new TestData(new int[]{0}, new int[]{1}, 0, 1));
        list.add(new TestData(new int[]{2, 0}, new int[]{1}, 1, 1));
        list.add(new TestData(new int[]{0, 0, 0, 0, 0}, new int[]{1, 2, 3, 4, 5}, 0, 5));
        list.add(new TestData(new int[]{4, 0, 0, 0, 0, 0}, new int[]{1, 2, 3, 5, 6}, 1, 5));
        list.add(new TestData(new int[]{4, 5, 6, 0, 0, 0}, new int[]{1, 2, 3}, 3, 3));
        list.add(new TestData(new int[]{1, 2, 4, 5, 6, 0}, new int[]{3}, 5, 1));
        list.add(new TestData(new int[]{0, 0, 3, 0, 0, 0, 0, 0, 0}, new int[]{-1, 1, 1, 1, 2, 3}, 3, 6));
        list.add(new TestData(new int[]{0, 2, 0, 0, 0, 0, 0}, new int[]{-1, -1, 2, 5, 6}, 2, 5));
        list.add(new TestData(new int[]{-1, -1, 0, 0, 0, 0}, new int[]{-1, 0}, 4, 2));
        return list;
    }

    private static boolean validateData(TestData td) {
        return (1 == td.nums1.length && 0 == td.nums2.length);
    }

    public static void main(String[] args) {
        List<TestData> list = prepareTestData();
        for (TestData td : list) {
            System.out.println("First array: " + Arrays.toString(td.nums1) + "\tSecond array: " + Arrays.toString(td.nums2) + "\tm: " + td.m + "\tn: " + td.n);
            if (!validateData(td)) merge(td.nums1, td.m, td.nums2, td.n);
            System.out.println("Merged array: " + Arrays.toString(td.nums1));
            System.out.println();
        }
    }

    private static void merge(int[] nums1, int m, int[] nums2, int n) {
        int pnt1 = m - 1;//last valid value in the first array
        int pnt2 = n - 1;//last element in the second array
        int index = m + n - 1;//last index of the first array
        while (pnt1 > -1 && pnt2 > -1) {
            if (nums1[pnt1] > nums2[pnt2]) {
                nums1[index--] = nums1[pnt1--];
            } else {
                nums1[index--] = nums2[pnt2--];
            }
        }
        while (pnt2 > -1) {
            nums1[index--] = nums2[pnt2--];
        }
    }

    private record TestData(int[] nums1, int[] nums2, int m, int n) {
    }
}
