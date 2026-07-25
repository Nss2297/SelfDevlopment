package com.shoppingService.Test;

public class Test519 {
    private static void fitRowIntoCol(int[] array, int col, int noOfRows, int[][] matrix) {
        for (int row = 0; row < noOfRows; row++) {
            matrix[row][col] = array[row];
        }
    }

    private static int[] populateArray(int[] array) {
        int[] row = new int[array.length];
        for (int a = 0; a < array.length; a++) {
            row[a] = array[a];
        }
        return row;
    }

    private static boolean limitCheck(int[] array) {
        for (int a = 0; a < array.length; a++) {
            if (array[a] < -1000 || array[a] > 1000) {
                System.out.println("Not withing limit.");
                return false;
            }
        }
        return true;
    }

    private static void rotateImage(int noOfRows, int noOfCols, int num, int[][] matrix) {
        if (limitCheck(matrix[num])) {
            int[] row = populateArray(matrix[num]);
            int colIndex = noOfCols - 1 - num;
            if (num + 1 < noOfRows) rotateImage(noOfRows, noOfCols, num + 1, matrix);
            fitRowIntoCol(row, colIndex, noOfRows, matrix);
        }
    }

    private static void rotate(int[][] matrix) {
        if (matrix.length == matrix[0].length && matrix.length < 21)
            rotateImage(matrix.length, matrix[0].length, 0, matrix);
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
//                        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        int[][] matrix = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        int[][] matrix = new int[][]{{1001, 0}, {5, 4}};
        displayMatrix(matrix);
        rotate(matrix);
        System.out.println();
        displayMatrix(matrix);
    }
}
