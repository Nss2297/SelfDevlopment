package com.shoppingService.dsaPath.arrays.day5;


public class LeetcodeQuestion50ImplementPowXRaisedToN {
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
        LeetcodeQuestion50ImplementPowXRaisedToN xRaisedToThePowerN = new LeetcodeQuestion50ImplementPowXRaisedToN();
        System.out.println("Pow(2.00000,10)=" + xRaisedToThePowerN.myPow(2.00000, 10));
        System.out.println("Pow(2.10000,3)=" + xRaisedToThePowerN.myPow(2.10000, 3));
        System.out.println("Pow(2.00000,-2)=" + xRaisedToThePowerN.myPow(2.00000, -2));
        System.out.println("Pow(0.00000,1)=" + xRaisedToThePowerN.myPow(0.00000, 1));
        System.out.println("Pow(8.84372, -5)=" + xRaisedToThePowerN.myPow(8.84372, -5));
        System.out.println("Pow(0.00001,2147483647)=" + xRaisedToThePowerN.myPow(0.00001, 2147483647));
        System.out.println("Pow(1.00000, 2147483647)=" + xRaisedToThePowerN.myPow(1.00000, 2147483647));
        System.out.println("Pow(2.00000, -2147483648)=" + xRaisedToThePowerN.myPow(2.00000, -2147483648));
        System.out.println("Pow(34.00515, -3)=" + xRaisedToThePowerN.myPow(34.00515, -3));
        System.out.println("Pow(-1.00000, 2147483647)=" + xRaisedToThePowerN.myPow(-1.00000, 2147483647));
    }
}
