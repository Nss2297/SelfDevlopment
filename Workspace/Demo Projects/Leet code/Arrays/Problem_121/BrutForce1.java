package com.shoppingService.LeetCode.Arrays.Problem_121;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrutForce1 {
    private static int generateSum(int[] array) {
        int max = 0;
        for (int a = 0; a < array.length; a++) {
            for (int s = a + 1; s < array.length; s++) {
                int buy = array[a];
                int sell = array[s];
                int profit = sell - buy;
                if (profit > max) max = profit;
            }
        }
        return max;
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
        if (validatePrices(prices)) return generateSum(prices);
        return 0;
    }

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{7, 1, 5, 3, 6, 4});
        list.add(new int[]{7, 6, 4, 3, 1});
        list.add(new int[]{1});
        list.add(new int[]{2, 1, 2, 0, 1});
        for (int[] array : list) {
            System.out.println("Stock prices: " + Arrays.toString(array) + " Max profit: " + sellStock(array));
        }
    }
}

