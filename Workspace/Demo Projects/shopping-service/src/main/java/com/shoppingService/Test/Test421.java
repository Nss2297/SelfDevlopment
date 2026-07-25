package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test421 {
    private static List<List<Integer>> generate(int num) {
        List<List<Integer>> list = new ArrayList<>();
        if (num >= 1 && num <= 30) {
            for (int a = 0; a < num; a++) {
                int size = a + 1;
                List<Integer> subList = new ArrayList<>();
                if (1 == size) {
                    subList.add(1);
                } else {
                    subList.add(1);
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
                int num = 5;
//        int num = 1;
        System.out.println(num);
        System.out.println(generate(num));
    }
}
