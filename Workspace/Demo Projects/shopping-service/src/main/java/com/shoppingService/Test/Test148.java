package com.shoppingService.Test;

import com.shoppingService.LeetCode.Problem_50.XRaisedToThePowerN;

public class Test148 {
    public double myPow(double x, int n) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        if (n == 1) return x;
        double result = 1;
        long powerN = n;
        if (powerN < 0) {
            powerN = -powerN;
            x = 1 / x;
        }
        while (powerN > 0) {
            if (powerN % 2 == 1) {
                result = result * x;
            }
            x = x * x;
            powerN = powerN / 2;
        }
        return result;
    }

    public static void main(String[] args) {
        Test148 test148 = new Test148();
        System.out.println("Pow(2.00000,10)=" + test148.myPow(2.00000, 10));
        System.out.println("Pow(2.10000,3)=" + test148.myPow(2.10000, 3));
        System.out.println("Pow(2.00000,-2)=" + test148.myPow(2.00000, -2));
        System.out.println("Pow(0.00000,1)=" + test148.myPow(0.00000, 1));
        System.out.println("Pow(8.84372, -5)=" + test148.myPow(8.84372, -5));
        System.out.println("Pow(0.00001,2147483647)=" + test148.myPow(0.00001, 2147483647));
        System.out.println("Pow(1.00000, 2147483647)=" + test148.myPow(1.00000, 2147483647));
        System.out.println("Pow(2.00000, -2147483648)=" + test148.myPow(2.00000, -2147483648));
        System.out.println("Pow(34.00515, -3)=" + test148.myPow(34.00515, -3));
        System.out.println("Pow(-1.00000, 2147483647)=" + test148.myPow(-1.00000, 2147483647));
    }
}
