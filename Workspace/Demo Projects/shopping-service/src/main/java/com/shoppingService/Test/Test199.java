package com.shoppingService.Test;

import java.util.Arrays;
import java.util.HashSet;

public class Test199 {
    public static void main(String[] argws) {
        int[] arrData = new int[]{1, 2, 2, 3, 4, 4, 4, 5, 5};
        HashSet<Integer> distictEle = new HashSet<>();
        for (int a = 0; a < arrData.length; a++) {
            if (!distictEle.contains(arrData[a])) distictEle.add(arrData[a]);
        }
        System.out.println(Arrays.toString(arrData));
        distictEle.forEach(ele -> System.out.println(ele));
    }
}
