package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test442 {
    private static List<List<Integer>> generate(int num) {
        List<List<Integer>> list = new ArrayList<>();
        if (num > 0 && num < 31) {
            for (int a = 0; a < num; a++) {
                int size = a + 1;
                List<Integer> subList = new ArrayList<>();
                subList.add(1);
                if (size > 1) {
                    List<Integer> prevList = list.get(size - 2);
                    for (int s = 1; s < prevList.size(); s++) {
                        int num1 = prevList.get(s - 1);
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
        System.out.println(1);
        System.out.println(generate(1));
        System.out.println(5);
        System.out.println(generate(5));
    }
}
