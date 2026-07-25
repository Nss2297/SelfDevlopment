package com.shoppingService.Test;

public class Test595 {
    private static double myPow(double num, int power) {
        if (0 == num) return 0;
        if (1 == num) return 1;
        if (0 == power) return 1;
        if (1 == power) return num;
        double result = 1;
        long npower = power;
        if (npower < 0) {
            npower = -npower;
            num = 1 / num;
        }
        while (npower > 0) {
            if (1 == npower % 2) result = num * result;
            num *= num;
            npower = npower / 2;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Pow(2.00000,10)=" + myPow(2.00000, 10));
        System.out.println("Pow(2.10000,3)=" + myPow(2.10000, 3));
        System.out.println("Pow(2.00000,-2)=" + myPow(2.00000, -2));
        System.out.println("Pow(0.00000,1)=" + myPow(0.00000, 1));
        System.out.println("Pow(8.84372, -5)=" + myPow(8.84372, -5));
        System.out.println("Pow(0.00001,2147483647)=" + myPow(0.00001, 2147483647));
        System.out.println("Pow(1.00000, 2147483647)=" + myPow(1.00000, 2147483647));
        System.out.println("Pow(2.00000, -2147483648)=" + myPow(2.00000, -2147483648));
        System.out.println("Pow(34.00515, -3)=" + myPow(34.00515, -3));
        System.out.println("Pow(-1.00000, 2147483647)=" + myPow(-1.00000, 2147483647));
    }
}
