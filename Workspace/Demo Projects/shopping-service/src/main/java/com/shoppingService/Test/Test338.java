package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test338 {
    private static List<Integer> combination(int r) {
        List<Integer> list = new ArrayList<>();
        int ans = 1;
        list.add(ans);
        for (int i = 1; i < r; i++) {
            ans *= r - i;
            ans /= i;
            list.add(ans);
        }
        return list;
    }

    private static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            ans.add(combination(i));
        }
        return ans;
    }

    public static void main(String[] args) {
        int num = 5;
//        int num=1;
        System.out.println(num);
        System.out.println(generate(num));
    }
}
