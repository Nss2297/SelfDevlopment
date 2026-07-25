package com.shoppingService.Test;

import com.shoppingService.streams.easy.Problem5FindAverageSalaryByDepartment;
import com.shoppingService.streams.easy.Problem6FindSecondHighestSalary;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test543 {
    public static void main(String[] args) {
        List<String> words = List.of("apple", "banana", "apple", "orange", "banana", "apple");
        System.out.println(words);
        System.out.println(words.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        record Employee(String name, String dept, int salary) {
        }

        List<Employee> employees = List.of(new Employee("Alice", "IT", 80000), new Employee("Bob", "HR", 60000), new Employee("Charlie", "IT", 95000), new Employee("David", "HR", 70000), new Employee("Eve", "Finance", 75000));
        System.out.println(employees);
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::dept, Collectors.maxBy(Comparator.comparing(Employee::salary)))));

        List<String> words2 = List.of("hello", "world");
        System.out.println(words2);
        List<String> listOfChars = words2.stream().flatMap(word -> word.chars().mapToObj(c -> String.valueOf((char) c))).distinct().toList();
        System.out.println(listOfChars);

        Map<String, Integer> scores = Map.of("Alice", 90, "Bob", 70, "Charlie", 85);
        System.out.println(scores);
        scores.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach(score -> {
            System.out.println(score.getKey() + "=" + score.getValue());
        });

        record Employee1(String dept, Integer salary) {
        }
        List<Employee1> emps = List.of(new Employee1("A", 1000), new Employee1("B", 3000), new Employee1("A", 2000), new Employee1("A", 8000), new Employee1("B", 4000));
        Map<String, Double> map = emps.stream().collect(Collectors.groupingBy(Employee1::dept, Collectors.averagingDouble(Employee1::salary)));
        System.out.println(map);

        record Employee2(String name, BigDecimal salary) {
        }
        List<Employee2> emps2 = List.of(new Employee2("A", BigDecimal.valueOf(1000)), new Employee2("B", BigDecimal.valueOf(2000)), new Employee2("A", BigDecimal.valueOf(3000)), new Employee2("A", BigDecimal.valueOf(4000)), new Employee2("B", BigDecimal.valueOf(5000)));
        System.out.println(emps2);
        emps2.stream().sorted(Comparator.comparing(Employee2::salary).reversed()).skip(1).findFirst().ifPresentOrElse(System.out::println,null);

        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
        System.out.println(nums);
        System.out.println(nums.stream().collect(Collectors.partitioningBy(num->num%2==0)));

        List<Integer> nums2 = List.of(2, 3, 4, 5);
        System.out.println(nums2);
        System.out.println(nums2.stream().map(num->num*num).toList());

        List<Integer> nums3 = List.of(1, 2, 3, 4, 5, 6);
        System.out.println(nums3);
        System.out.println(nums3.stream().filter(num->0==num%2).toList());

        List<Integer> nums4 = List.of(5, 12, 18, 7, 10, 22);
        System.out.println(nums4);
        System.out.println(nums4.stream().filter(num->num>10).count());

        List<String> words1 = List.of("cat", "elephant", "dog", "hippopotamus");
        System.out.println(words1);
        System.out.println(words1.stream().collect(Collectors.groupingBy(String::length)).entrySet().stream().sorted().toList());
    }
}
