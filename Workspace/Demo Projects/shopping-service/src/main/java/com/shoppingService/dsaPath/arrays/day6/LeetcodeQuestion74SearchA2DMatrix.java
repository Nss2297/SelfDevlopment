package com.shoppingService.dsaPath.arrays.day6;

import java.util.Arrays;

public class LeetcodeQuestion74SearchA2DMatrix {
    private static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length < 1) return false;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == target) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        int[][] matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int[][] matrix = new int[][]{{1}, {3}};
        int target = 3;
//        int target = 13;
        System.out.println(Arrays.toString(matrix));
        System.out.println(target);
        System.out.println(searchMatrix(matrix, target));
    }
}
