package com.shoppingService.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test307 {
    private static Map<Character, Integer> countCharacters(String str) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int a = 0; a < str.length(); a++) {
            char ch = str.charAt(a);
            if (!map.containsKey(ch)) {
                map.put(ch, 1);
            } else {
                map.put(ch, map.get(ch) + 1);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String str = "Hello World OL";
        System.out.println(str);
        System.out.println(countCharacters(str.replace(" ", "")));
    }
}
