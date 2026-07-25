package com.shoppingService.Test;

import com.shoppingService.LeetCode.Problem_48.RotateImage2;

public class Test614 {
    private static void fitRowIntoCol(int rows, int[] array, int col, int[][] matrix) {
        for (int row = 0; row < rows; row++) {
            matrix[row][col] = array[row];
        }
    }

    private static int[] populateArrray(int size, int[] array) {
        int[] row = new int[size];
        for (int a = 0; a < size; a++) {
            row[a] = array[a];
        }
        return row;
    }

    private static void rotateImage(int noOfRows, int noOfCols, int rowIndex, int[][] matrix) {
        int[] row = populateArrray(noOfCols, matrix[rowIndex]);
        int col = noOfCols - 1 - rowIndex;
        if (rowIndex + 1 < noOfRows) rotateImage(noOfRows, noOfCols, rowIndex + 1, matrix);
        fitRowIntoCol(noOfRows, row, col, matrix);
    }

    private static boolean limitCheck(int noOfRows, int noOfCols, int[][] matrix) {
        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                if (matrix[row][col] > 1000 || matrix[row][col] < -1000) {
                    System.out.println(matrix[row][col] + " at matrix[" + row + "][" + col + "] is out of limit.");
                    return false;
                }
            }
        }
        return true;
    }

    private static void rotate(int noOfRows, int noOfCols, int[][] matrix) {
        if (noOfRows == noOfCols && noOfRows < 21 && limitCheck(noOfRows, noOfCols, matrix))
            rotateImage(noOfRows, noOfCols, 0, matrix);
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
        RotateImage2 rotateImage = new RotateImage2();
//        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        int[][] matrix = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
//        int[][] matrix = new int[][]{{1001, 0}, {5, 4}};
        int[][] matrix = new int[][]{{1000, 0}, {5, -4000}};
        displayMatrix(matrix);
        rotate(matrix.length, matrix[0].length, matrix);
        System.out.println();
        displayMatrix(matrix);
    }
}
