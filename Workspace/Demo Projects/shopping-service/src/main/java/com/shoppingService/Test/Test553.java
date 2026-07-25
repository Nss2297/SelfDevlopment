package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test553 {
    private static List<List<Integer>> generate(int noOfRows) {
        List<List<Integer>> list = new ArrayList<>();
        if (noOfRows > 0 && noOfRows < 31) {
            for (int row = 0; row < noOfRows; row++) {
                List<Integer> subList = new ArrayList<>();
                subList.add(1);
                if (row > 0) {
                    List<Integer> prevList = list.get(row - 1);
                    for (int a = 1; a < prevList.size(); a++) {
                        int num1 = prevList.get(a - 1);
                        int num2 = prevList.get(a);
                        subList.add(num1 + num2);
                    }
                    subList.add(1);
                }
                list.add(subList);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(generate(1));
        System.out.println(5);
        System.out.println(generate(5));
    }
}
