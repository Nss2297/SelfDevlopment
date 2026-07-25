package com.shoppingService.java8;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8 {

    static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
       generateValue();
       employees.stream().forEach(System.out::println);

        System.out.println("--------------------------------------------------");

       int totalSalary =  employees.stream().map(employee -> employee.getSalary()).reduce(0,(subtotal,salary) -> subtotal+salary);
       System.out.println(totalSalary);

        System.out.println("--------------------------------------------------");

        Map<Long,Department> bonusMap  = employees.stream().map(employee -> employee.getDepartment()).distinct().collect(Collectors.toMap(Department::getId, Function.identity()));
        System.out.println(bonusMap);

        System.out.println("--------------------------------------------------");

       final Map<String,List<Integer>> bonusMapa = new HashMap<>();
       employees.stream().map(employee -> employee.getDepartment()).forEach(department -> bonusMapa.put(department.getName(),department.getBonuses()));
        bonusMapa.entrySet().stream().forEach(System.out::println);

        System.out.println("--------------------------------------------------");

       List<Integer> bonuses = employees.stream().map(employee -> employee.getDepartment().getBonuses()).flatMap(integers -> integers.stream()).collect(Collectors.toList());
        bonuses.forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        int maxBonus = employees.stream().map(employee -> employee.getDepartment()).max(Comparator.comparing(Department::getBonus))
                .get().getBonus();
        System.out.println(maxBonus);

        Map<String,Integer> maxBonusWithName = employees.stream().map(employee -> employee.getDepartment()).sorted(Comparator.comparing(Department::getBonus).reversed()).limit(1)
                .collect(Collectors.toMap(Department::getName,Department::getBonus));
        System.out.println(maxBonusWithName);

        System.out.println("--------------------------------------------------");

        List<String> product = Arrays.asList("Apple","Samsung","Apple","MI","Sony","Samsung");

         Map<String,Long> maps =  product.stream().collect(Collectors.groupingBy(p->p,Collectors.counting()));
        String firstUniqueProduct =  maps.entrySet().stream().filter(s -> s != null).filter(entry -> entry.getValue() == 1).findFirst().get().getKey();
        System.out.println(firstUniqueProduct);
//        maps.entrySet().stream().filter(s -> s != null).forEach(System.out::println);

    }









    private static void generateValue(){
     Department it = new Department(1L,"IT",Arrays.asList(20),20);
     Department acc = new Department(2l,"Account",Arrays.asList(10),10);
     Department sales = new Department(3L,"Sales",Arrays.asList(5),5);
     Department HR = new Department(4L,"HR",Arrays.asList(5),5);

     Employee emp1 = new Employee(1l,"Jaymin",10000,it,Designation.STL);
        Employee emp2 = new Employee(2l,"Parth",7000,it,Designation.TL);
        Employee emp3 = new Employee(3l,"Kuntal",5000,it,Designation.TL);
        Employee emp4 = new Employee(4l,"Jinal",3000,it,Designation.SSE);
        Employee emp5 = new Employee(5l,"Janesh",2000,it,Designation.SE);
        Employee emp6 = new Employee(6l,"Ayush",10000,acc,Designation.SE);
        Employee emp7 = new Employee(7l,"Sarthak",10000,sales,Designation.SALES);
        Employee emp8 = new Employee(8l,"Hit",10000,sales,Designation.SALES);
        Employee emp9 = new Employee(9l,"Poorva",10000,HR,Designation.HR);

        employees.addAll(Arrays.asList(emp1,emp2,emp3,emp4,emp5,emp6,emp7,emp8,emp9));

    };
}
