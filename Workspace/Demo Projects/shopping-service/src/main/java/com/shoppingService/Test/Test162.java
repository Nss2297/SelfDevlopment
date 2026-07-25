package com.shoppingService.Test;

public class Test162 {
    private static double myPow(double num, int exp) {
        if (num == 0) return 0;
        if (exp == 0) return 1;
        if (exp == 1) return num;
        long powerN = exp;
        if (exp < 0) {
            num = 1 / num;
            powerN = -powerN;
        }
        double result = 1;
        while (powerN > 0) {
            if (powerN % 2 == 1) {
                result = result * num;
            }
            num = num * num;
            powerN = powerN / 2;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Pow(2.00000,10)=" + myPow(2.00000, 10));
        System.out.println("Pow(2.10000,3)=" + myPow(2.10000, 3));
        System.out.println("Pow(2.00000,-2)=" + myPow(2.00000, -2));
        System.out.println("Pow(0.00000,1)=" + myPow(0.00000, 1));
        System.out.println("Pow(0.00000,1)=" + myPow(8.84372, -5));
        System.out.println("Pow(0.00001,2147483647)=" + myPow(0.00001, 2147483647));
        System.out.println("Pow(1.00000, 2147483647)=" + myPow(1.00000, 2147483647));
        System.out.println("Pow(2.00000, -2147483648)=" + myPow(2.00000, -2147483648));
        System.out.println("Pow(34.00515, -3)=" + myPow(34.00515, -3));
    }
}
