package com.shoppingService.LeetCode.Arrays.Problem_50;


public class XRaisedToThePowerN {
    public static void main(String[] args) {
        XRaisedToThePowerN xRaisedToThePowerN = new XRaisedToThePowerN();
        double[] num = new double[]{2.00000, 2.10000, 2.00000, 0.00000, 8.84372, 0.00001, 1.00000, 2.00000, 34.00515, -1.00000};
        int[] power = new int[]{10, 3, -2, 1, -5, 2147483647, 2147483647, -2147483648, -3, 2147483647};
        for (int a = 0; a < num.length; a++) {
            System.out.println("Pow(" + num[a] + "," + power[a] + ")=" + xRaisedToThePowerN.myPow(num[a], power[a]));
        }
    }

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
}
