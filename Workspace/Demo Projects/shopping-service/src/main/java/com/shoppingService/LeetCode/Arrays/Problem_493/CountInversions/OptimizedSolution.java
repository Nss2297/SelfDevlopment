package com.shoppingService.LeetCode.Arrays.Problem_493.CountInversions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptimizedSolution {
    private static List<TestData> populateTestData() {
        List<TestData> list = new ArrayList<>();
        list.add(new TestData(new int[]{1, 2, 3, 4, 5}));
        list.add(new TestData(new int[]{5, 4, 3, 2, 1}));
        list.add(new TestData(new int[]{5, 3, 2, 1, 4}));
        list.add(new TestData(new int[]{5, 6, 2, 3}));
        list.add(new TestData(new int[]{2, 4, 8, 2, 4}));
        return list;
    }

    public static void main(String[] args) {
        List<TestData> list = populateTestData();
        for (TestData td : list) {
            System.out.println("Array: " + Arrays.toString(td.array) + "\tPairs: " + mergeSortAndCountParis(0, td.array.length - 1, td.array));
        }
    }

    private static int mergeSortAndCountParis(int low, int high, int[] array) {
        int noOfPairs = 0;
        if (low < high) {
            int median = low + (high - low) / 2;
            noOfPairs += mergeSortAndCountParis(low, median, array);
            noOfPairs += mergeSortAndCountParis(median + 1, high, array);
            noOfPairs += mergeAndCountPairs(low, high, median, array);
        }
        return noOfPairs;
    }


    private static int mergeAndCountPairs(int low, int high, int median, int[] array) {
        int[] leftArray = populateArray(median - low + 1, low, array);
        int[] rightArray = populateArray(high - median, median + 1, array);
        int leftPnt = 0;
        int rightPnt = 0;
        int pnt = low;
        int pnt1 = low;
        int noOfPairs = 0;
        while (leftPnt < leftArray.length && rightPnt < rightArray.length) {
            if (leftArray[leftPnt] <= rightArray[rightPnt]) {
                array[pnt] = leftArray[leftPnt];
                ++leftPnt;
                ++pnt1;
            } else {
                array[pnt] = rightArray[rightPnt];
                noOfPairs += (median - pnt1) + 1;
                ++rightPnt;
            }
            ++pnt;
        }
        while (leftPnt < leftArray.length) {
            array[pnt] = leftArray[leftPnt];
            ++leftPnt;
            ++pnt;
        }
        while (rightPnt < rightArray.length) {
            array[pnt] = rightArray[rightPnt];
            ++rightPnt;
            ++pnt;
        }
        return noOfPairs;
    }

    private static int[] populateArray(int size, int startIndex, int[] orignalArray) {
        int[] array = new int[size];
        for (int a = 0; a < size; a++) {
            array[a] = orignalArray[a + startIndex];
        }
        return array;
    }

    private static record TestData(int[] array) {
    }
}
