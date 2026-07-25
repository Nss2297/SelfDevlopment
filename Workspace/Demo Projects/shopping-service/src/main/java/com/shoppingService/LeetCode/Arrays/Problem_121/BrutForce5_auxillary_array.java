package com.shoppingService.LeetCode.Arrays.Problem_121;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrutForce5_auxillary_array {
    private static int calculateProfit(int[] array) {
        //populate auxillary array
        int[] auxArray = new int[array.length];
        for (int a = array.length - 1; a > -1; a--) {
            if (a == array.length - 1) {
                auxArray[a] = array[a];
            } else {
                int num1 = array[a];
                int num2 = auxArray[a + 1];
                auxArray[a] = num1 > num2 ? num1 : num2;
            }
        }
        int maxProfit = 0;
        for (int s = 0; s < array.length; s++) {
            maxProfit = (auxArray[s] - array[s]) > maxProfit ? (auxArray[s] - array[s]) : maxProfit;
        }
        return maxProfit;
    }

    private static boolean validatePrices(int[] prices) {
        if (prices.length < 2 || prices.length > 1000000) {
            System.out.println("Invalid array length.");
            return false;
        }
        for (int index = 0; index < prices.length; index++) {
            if (prices[index] < 0 || prices[index] > 100000) {
                System.out.println("Invalid price.");
                return false;
            }
        }
        return true;
    }

    private static int sellStock(int[] prices) {
        if (validatePrices(prices)) return calculateProfit(prices);
        return 0;
    }

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{7, 1, 5, 3, 6, 4});
        list.add(new int[]{7, 6, 4, 3, 1});
        list.add(new int[]{1});
        list.add(new int[]{2, 1, 2, 0, 1});
        list.add(new int[]{5, 2, 6, 1, 4});
        list.add(new int[]{3, 1, 4, 8, 7, 2, 5});
        for (int[] array : list) {
            System.out.println("Stock prices: " + Arrays.toString(array) + " Max profit: " + sellStock(array));
        }
    }
}
