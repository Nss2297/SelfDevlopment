package com.shoppingService.Test;

public class Test316 {
    private static void setZeroes(int[][] matrix) {
        boolean zeroInFirstRow = false;
        boolean zeroInFirstColumn = false;
        int rowLength = matrix[0].length;
        int columnLength = matrix.length;
        for (int col = 0; col < rowLength; col++) {
            if (0 == matrix[0][col]) {
                zeroInFirstRow = true;
                break;
            }
        }
        for (int row = 0; row < columnLength; row++) {
            if (0 == matrix[row][0]) {
                zeroInFirstColumn = true;
                break;
            }
        }
        for (int row = 1; row < columnLength; row++) {
            for (int col = 1; col < rowLength; col++) {
                if (0 == matrix[row][col]) {
                    matrix[0][col] = 0;
                    matrix[row][0] = 0;
                }
            }
        }
        for (int row = 1; row < columnLength; row++) {
            for (int col = 1; col < rowLength; col++) {
                if (0 == matrix[0][col] || 0 == matrix[row][0]) matrix[row][col] = 0;
            }
        }
        if (zeroInFirstColumn) {
            for (int row = 0; row < columnLength; row++) {
                matrix[row][0] = 0;
            }
        }
        if (zeroInFirstRow) {
            for (int col = 0; col < rowLength; col++) {
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
