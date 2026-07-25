package com.shoppingService.java8;

public class Employee {

    private Long id;
    private String name;
    private int salary;
    private Department department;
    private Designation designation;

    public Employee(Long id, String name, int salary, Department department, Designation designation) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.designation = designation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return this.id +" "+this.name+" "+this.salary+" "+this.department.getName()+" "+this.designation.name;
    }
}
