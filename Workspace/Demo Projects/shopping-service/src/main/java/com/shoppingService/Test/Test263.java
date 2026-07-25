package com.shoppingService.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Test263 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("alice", "bob", "charlie");
        System.out.println(list);
        System.out.println(list.stream().map(String::toUpperCase).toList());
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(nums);
        System.out.println(nums.stream().filter(num -> num % 2 == 0).toList());
        List<String> fruits = Arrays.asList("apple", "banana", "mango");
        System.out.println(fruits);
        System.out.println(fruits.stream().findFirst().orElse(null));
        List<String> list2 = Arrays.asList("a", "b", "c", "d");
        System.out.println(list2.stream().count());
        System.out.println(list2);
        List<Integer> nums2 = Arrays.asList(5, 3, 8, 1);
        System.out.println(nums2);
        System.out.println(nums2.stream().sorted().toList());
        System.out.println(nums2.stream().sorted(Comparator.reverseOrder()).toList());
        List<Integer> nums3 = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        System.out.println(nums3);
        System.out.println(nums3.stream().distinct().toList());
        List<String> words = Arrays.asList("Java", "Streams", "Practice");
        System.out.println(words);
        System.out.println(words.stream().collect(Collectors.joining(", ")));
        List<Integer> nums4 = Arrays.asList(2, 3, 4);
        System.out.println(nums4);
        System.out.println(nums4.stream().map(num -> num * num).toList());
        List<Integer> nums5 = Arrays.asList(10, 25, 3, 7);
        System.out.println(nums5);
        System.out.println(nums5.stream().max(Integer::compare).get());
        List<Integer> nums6 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(nums6);
        System.out.println(nums6.stream().mapToInt(Integer::intValue).sum());
        List<String> words2 = Arrays.asList("java", "stream", "map", "filter", "reduce");
        System.out.println(words2);
        System.out.println(words2.stream().collect(Collectors.groupingBy(String::length)));
        List<Integer> nums7 = Arrays.asList(1, 2, 3, 4, 2, 3, 5, 6, 1);
        System.out.println(nums7);
        System.out.println(nums7.stream().collect(Collectors.groupingBy(num -> num, Collectors.counting())).entrySet().stream().filter(map -> map.getValue() > 1).toList());
        List<Integer> nums8 = Arrays.asList(15, 3, 27, 12, 99, 54, 33);
        System.out.println(nums8);
        System.out.println(nums8.stream().sorted(Comparator.reverseOrder()).limit(3).toList());
        List<String> words1 = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        System.out.println(words1);
        System.out.println(words1.stream().collect(Collectors.groupingBy(str -> str, Collectors.counting())));
        List<Integer> nums9 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println(nums9);
        System.out.println(nums9.stream().collect(Collectors.partitioningBy(num -> num % 2 == 0)));
        List<String> words3 = Arrays.asList("cat", "elephant", "dog", "hippopotamus");
        System.out.println(words3);
        System.out.println(words3.stream().max(Comparator.comparing(String::length)).orElse(null));
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );
        System.out.println(listOfLists);
        System.out.println(listOfLists.stream().flatMap(List::stream).toList());
        record Employee(String name, String dept, int salary) {
        }
        List<Employee> emps = Arrays.asList(
                new Employee("Alice", "IT", 5000),
                new Employee("Bob", "IT", 7000),
                new Employee("Charlie", "HR", 4000),
                new Employee("David", "HR", 4500)
        );
        System.out.println(emps);
        System.out.println(emps.stream().collect(Collectors.groupingBy(Employee::dept, Collectors.averagingInt(Employee::salary))));
        List<Integer> nums10 = Arrays.asList(10, 20, 35, 40, 50, 50);
        System.out.println(nums10);
        System.out.println(nums10.stream().distinct().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst().orElse(null));
        List<String> names = Arrays.asList("Alex", "John", "Christopher", "Bob");
        System.out.println(names);
        System.out.println(names.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.joining("-")));
        List<String> words4 = List.of("apple", "banana", "apple", "orange", "banana", "apple");
        System.out.println(words4);
        System.out.println(words4.stream().collect(Collectors.groupingBy(word -> word, Collectors.counting())));
        List<Employee> employees = List.of(
                new Employee("Alice", "IT", 80000),
                new Employee("Bob", "HR", 60000),
                new Employee("Charlie", "IT", 95000),
                new Employee("David", "HR", 70000),
                new Employee("Eve", "Finance", 75000)
        );
        System.out.println(employees);
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::dept, Collectors.maxBy(Comparator.comparingInt(Employee::salary)))).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, map -> map.getValue().orElse(null))));
    }
}
