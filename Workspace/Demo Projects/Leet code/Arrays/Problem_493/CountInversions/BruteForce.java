package com.shoppingService.LeetCode.Arrays.Problem_493.CountInversions;

import com.shoppingService.Test.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce {
    private static List<TestData> populateTestData() {
        List<TestData> list = new ArrayList<>();
        list.add(new TestData(new int[]{5, 3}, new int[]{2, 1, 4}));
        list.add(new TestData(new int[]{5, 6, 2, 3}, new int[]{2, 4, 8, 2, 4}));
        return list;
    }

    public static void main(String[] args) {
        List<TestData> list = populateTestData();
        for (TestData td : list) {
            System.out.println("\n\nFirstArray: " + Arrays.toString(td.array1) + "\tSecondArray: " + Arrays.toString(td.array2) + "\tPairs: " + countPairs(td.array1, td.array2));
        }
    }

    private static int countPairs(int[] array1, int[] array2) {
        int cnt = 0;
        for (int a = 0; a < array1.length; a++) {
            for (int s = 0; s < array2.length; s++) {
                if (array1[a] > array2[s]) cnt += 1;
            }
        }
        return cnt;
    }

    private static record TestData(int[] array1, int[] array2) {
    }

}
