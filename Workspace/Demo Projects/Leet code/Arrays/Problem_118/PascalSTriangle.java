package com.shoppingService.LeetCode.Arrays.Problem_118;

import java.util.ArrayList;
import java.util.List;

public class PascalSTriangle {
    public static void main(String[] args) {
        PascalSTriangle pascalSTriangle = new PascalSTriangle();
        for (int num : new int[]{1, 2, 3, 4, 5, 6, 7}) {
            System.out.println("Rows:\t" + num + "\tTriangle: " + pascalSTriangle.generate(num));
        }
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        if (numRows >= 1 && numRows <= 30) {
            for (int a = 0; a < numRows; a++) {
                int size = a + 1;
                List<Integer> subList = new ArrayList<>();
                if (1 == size) {
                    subList.add(1);
                } else {
                    List<Integer> prevList = list.get(size - 2);
                    subList.addFirst(1);
                    for (int s = 1; s < prevList.size(); s++) {
                        int num1 = prevList.get(s - 1);
                        int num2 = prevList.get(s);
                        subList.add(s, num1 + num2);
                    }
                    subList.add(1);
                }
                list.add(subList);
            }
        }
        return list;
    }
}
