package com.shoppingService.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Test287 {
    private static Map<Character, Integer> calculateStringChar(String str) {
        Map<Character, Integer> map = new HashMap<>();
        str=str.replace(" ","");
        for (int a = 0; a < str.length(); a++) {
            char ch = str.charAt(a);
            if (!map.containsKey(ch)) map.put(ch, 0);
            if (map.containsKey(ch)) {
                int val = map.get(ch);
                map.put(ch, ++val);
            }
        }
        return map;
    }

    private static void displayData(Map<Character, Integer> map) {
        System.out.print(map);
    }

    public static void main(String[] args) {
//        list.stream().filter(obj->obj.getAge()>30).sorted(Comparator.comparingInt(obj.getAge()).reversed()).toList();
        Map<Character, Integer> map = calculateStringChar("Hello World");
        displayData(map);
    }
}
