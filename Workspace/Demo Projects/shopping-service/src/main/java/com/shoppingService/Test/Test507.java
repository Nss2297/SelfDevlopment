package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test507 {
    private static List<List<Integer>> generate(int n0OfRows) {
        List<List<Integer>> list = new ArrayList<>();
        if (n0OfRows > 0 && n0OfRows < 31) {
            for (int a = 1; a <= n0OfRows; a++) {
                int size = a;
                List<Integer> subList = new ArrayList<>();
                subList.add(1);
                if (size > 1) {
                    List<Integer> prevList = list.get(size - 2);
                    for (int s = 1; s < prevList.size(); s++) {
                        int num1 = prevList.get(s-1);
                        int num2 = prevList.get(s);
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
                int num = 5;
//        int num = 1;
        System.out.println(num);
        System.out.println(generate(num));
    }
}
