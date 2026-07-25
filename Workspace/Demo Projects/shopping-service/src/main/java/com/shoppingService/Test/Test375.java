package com.shoppingService.Test;

public class Test375 {
    private static void setZeroes(int[][] matrix) {
        boolean zeroInFirstCol = false;
        boolean zeroInFirstRow = false;
        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        for (int row = 0; row < rowLength; row++) {
            if (0 == matrix[row][0]) {
                zeroInFirstCol = true;
                break;
            }
        }
        for (int col = 0; col < colLength; col++) {
            if (0 == matrix[0][col]) {
                zeroInFirstRow = true;
                break;
            }
        }
        for (int row = 1; row < rowLength; row++) {
            for (int col = 1; col < colLength; col++) {
                if (0 == matrix[row][col]) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }
        for (int row = 1; row < rowLength; row++) {
            for (int col = 1; col < colLength; col++) {
                if (0 == matrix[row][0] || 0 == matrix[0][col]) matrix[row][col] = 0;
            }
        }
        if (zeroInFirstCol) {
            for (int row = 0; row < rowLength; row++) {
                matrix[row][0] = 0;
            }
        }
        if (zeroInFirstRow) {
            for (int col = 0; col < colLength; col++) {
                matrix[0][col] = 0;
            }
        }
    }

    private static void displayMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
//        int[][] matrix = new int[][]{{3, 1, 2}, {3, 0, 5}, {1, 3, 2}};
//        int[][] matrix = new int[][]{{0, 1, 2, 4}, {3, 8, 5, 4}, {1, 3, 2, 4}, {1, 3, 2, 0}};
//        int[][] matrix = new int[][]{{3, 1, 2, 4}, {3, 8, 0, 4}, {1, 3, 2, 4}, {1, 3, 2, 4}};
//        int[][] matrix = new int[][]{{0, 1}};
//        int[][] matrix = new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        int[][] matrix = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        displayMatrix(matrix);
        setZeroes(matrix);
        System.out.println();
        displayMatrix(matrix);
    }
}
