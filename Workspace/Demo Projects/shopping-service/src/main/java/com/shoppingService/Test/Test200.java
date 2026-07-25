package com.shoppingService.Test;

import org.bouncycastle.util.Integers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test200 {
    public static void main(String[] args) {
        int[] arrSecLarge = new int[]{12, 35, 1, 10, 34, 1};
        System.out.println(Arrays.toString(arrSecLarge));
        for (int a = 0; a < arrSecLarge.length; a++) {
            int minIndex = a;
            for (int s = a + 1; s < arrSecLarge.length; s++) {
                minIndex = arrSecLarge[minIndex] < arrSecLarge[s] ? minIndex : s;
            }
            int temp = arrSecLarge[minIndex];
            arrSecLarge[minIndex] = arrSecLarge[a];
            arrSecLarge[a] = temp;
        }
        System.out.println(Arrays.toString(arrSecLarge));
        System.out.println(arrSecLarge[arrSecLarge.length-2]);
    }

//@FunctionalInterface
//testInteface interface{
//
//     void test();
//    }
//}
//
//class Test implements testInterface{
//    @Override
//    public test(){
//        List<Integer> list =new ArrayList<>();
//        list.stream().filter(ele->ele%2==1).toList();
//    }
}