package com.shoppingService.LeetCode.Arrays.Problem_48;

import java.util.ArrayList;
import java.util.List;

public class RotateImage2 {
    public static void main(String[] args) {
        RotateImage2 rotateImage = new RotateImage2();
        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        list.add(new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}});
        list.add(new int[][]{{1001, 0}, {5, 4}});
        list.add(new int[][]{{1000, 0}, {5, -4000}});
        for (int[][] matrix : list) {
            rotateImage.displayMatrix(matrix);
            rotateImage.rotate(matrix);
            System.out.println();
            rotateImage.displayMatrix(matrix);
            System.out.println();
        }
    }

    private void fitRowIntoCol(int noOfRows, int[] array, int col, int[][] matrix) {
        for (int row = 0; row < noOfRows; row++) {
            matrix[row][col] = array[row];
        }
    }

    private int[] populateArray(int size, int[] array) {
        int[] row = new int[size];
        for (int index = 0; index < size; index++) {
            row[index] = array[index];
        }
        return row;
    }

    private void rotateImage(int noOfRows, int noOfCols, int rowIndex, int[][] matrix) {
        int[] row = populateArray(noOfCols, matrix[rowIndex]);
        int col = noOfCols - 1 - rowIndex;
        if (rowIndex + 1 < noOfRows)
            rotateImage(noOfRows, noOfCols, rowIndex + 1, matrix);
        fitRowIntoCol(noOfRows, row, col, matrix);
    }

    private boolean limitCheck(int noOfRows, int noOfCols, int[][] matrix) {
        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                if (matrix[row][col] < -1000 || matrix[row][col] > 1000) {
                    System.out
                            .println(matrix[row][col] + " at index matrix[" + row + "][" + col + "] is out of limit.");
                    return false;
                }
            }
        }
        return true;
    }

    public void rotate(int[][] matrix) {
        int noOfRows = matrix[0].length;
        int noOfCols = matrix.length;
        if (noOfRows == noOfCols && noOfRows < 21 && limitCheck(noOfRows, noOfCols, matrix))
            rotateImage(noOfRows, noOfCols, 0, matrix);
    }

    private void displayMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + "\t");
            }
            System.out.println();
        }
    }
}
