package com.shoppingService.Test;

public class Test531 {
    private static void fitRowIntoCol(int noOfRows, int[] array, int col, int[][] matrix) {
        for (int row = 0; row < noOfRows; row++) {
            matrix[row][col] = array[row];
        }
    }

    private static int[] populateArray(int size, int[] row) {
        int[] array = new int[size];
        for (int a = 0; a < size; a++) {
            array[a] = row[a];
        }
        return array;
    }

    private static boolean limitCheck(int size, int[] row) {
        for (int a = 0; a < size; a++) {
            if (row[a] < -1000 || row[a] > 1000) {
                System.out.println("Not withing limit.");
                return false;
            }
        }
        return true;
    }

    private static void rotateImage(int noOfRows, int noOfCols, int rowIndex, int[][] matrix) {
        if (limitCheck(matrix[rowIndex].length, matrix[rowIndex])) {
            int[] row = populateArray(matrix[rowIndex].length, matrix[rowIndex]);
            int colIndex = noOfCols - 1 - rowIndex;
            if (rowIndex + 1 < noOfRows) rotateImage(noOfRows, noOfCols, rowIndex + 1, matrix);
            fitRowIntoCol(noOfRows, row, colIndex, matrix);
        }
    }

    private static void rotate(int noOfRows, int noOfCols, int[][] matrix) {
        if (noOfRows == noOfCols && noOfRows < 21) rotateImage(noOfRows, noOfCols, 0, matrix);
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
//        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        int[][] matrix = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        int[][] matrix = new int[][]{{1001, 0}, {5, 4}};
        displayMatrix(matrix);
        rotate(matrix.length, matrix[0].length, matrix);
        System.out.println();
        displayMatrix(matrix);
    }
}
