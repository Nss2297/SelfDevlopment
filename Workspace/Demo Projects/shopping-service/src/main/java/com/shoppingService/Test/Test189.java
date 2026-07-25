package com.shoppingService.Test;

import java.util.Arrays;

public class Test189 {
    private static boolean searchTargetIn2DArray(int num, int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == num) return true;
            }
        }
        return false;
    }

    private static void display2DArray(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
                int[][] matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
//        int[][] matrix = new int[][]{{1}, {3}};
        int num = 3;
//        int num = 13;
        display2DArray(matrix);
        System.out.println(num);
        System.out.println(searchTargetIn2DArray(num, matrix));
    }
}
