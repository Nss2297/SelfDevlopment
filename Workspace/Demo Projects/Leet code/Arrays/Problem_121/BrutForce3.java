package com.shoppingService.LeetCode.Arrays.Problem_121;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrutForce3 {
    private static int calculateProfit(int buyPriceIndex, int sellDayIndex, int[] array, int maxProfit) {
        int profit = array[sellDayIndex] - array[buyPriceIndex];
        if (profit > maxProfit)
            maxProfit = profit;
        if (sellDayIndex == array.length - 1 && buyPriceIndex + 1 != array.length - 1) {
            buyPriceIndex = buyPriceIndex + 1;
            sellDayIndex = buyPriceIndex + 1;
        } else if (sellDayIndex < array.length && buyPriceIndex + 1 != array.length - 1) {
            sellDayIndex = sellDayIndex + 1;
        } else if (buyPriceIndex + 1 == array.length - 1) return maxProfit;
        return calculateProfit(buyPriceIndex, sellDayIndex, array, maxProfit);
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
        if (validatePrices(prices)) return calculateProfit(0, 1, prices, 0);
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
