package com.shoppingService.Test;

public class Test508 {
    private static void fitRowInCol(int noOfRows, int[] row, int colIndex, int[][] matrix) {
        for (int a = 0; a < noOfRows; a++) {
            matrix[a][colIndex] = row[a];
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

    private static void rotateImage(int noOfRows, int noOfCols, int rowNum, int[][] matrix) {
        if (limitCheck(matrix[rowNum])) {
            int[] row = populateArray(matrix[rowNum]);
            int colIndex = noOfCols - 1 - rowNum;
            if (rowNum + 1 < noOfRows) rotateImage(noOfRows, noOfCols, rowNum + 1, matrix);
            fitRowInCol(noOfRows, row, colIndex, matrix);
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
