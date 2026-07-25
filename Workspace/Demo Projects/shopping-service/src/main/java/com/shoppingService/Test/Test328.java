package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;

public class Test328 {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("aa");
        list1.add("cc");
        list1.add("dd");
        list2.add("aa");
        list2.add("dd");
        list2.add("cc");
        List<List<String>> list = new ArrayList<List<String>>();
        list.add(list1);
        list.add(list2);
        List<String> finalList = list.stream().flatMap(l -> l.stream()).filter(l -> l.equals("aa")).toList();
        System.out.println(list);
        System.out.println(finalList);
    }
}
