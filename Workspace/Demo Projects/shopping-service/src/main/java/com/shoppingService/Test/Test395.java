package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test395 {
    private static List<Integer> populateSubList(int num) {
        List<Integer> list = new ArrayList<>();
        int result = 1;
        list.add(1);
        for (int a = 1; a < num; a++) {
            result = result * (num - a) / a;
            list.add(result);
        }
        return list;
    }

    private static List<List<Integer>> generate(int num) {
        List<List<Integer>> list = new ArrayList<>();
        for (int a = 1; a <= num; a++) {
            list.add(populateSubList(a));
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(generate(1));
        System.out.println(5);
        System.out.println(generate(5));
        System.out.println(10);
        System.out.println(generate(10));
    }
}
