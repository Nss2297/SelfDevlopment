package com.shoppingService.LeetCode.Arrays.Problem_48;

public class RotateImage {
    private void fitRowAsColumn(int[] rowToFit, int colIndex, int rowLength, int[][] matrix) {
        for (int row = 0; row < rowLength; row++) {
            matrix[row][colIndex] = rowToFit[row];
        }
    }

    private int[] populateArray(int[] array) {
        int[] row = new int[array.length];
        for (int a = 0; a < array.length; a++) {
            row[a] = array[a];
        }
        return row;
    }

    private boolean limitCheck(int[] array) {
        for (int a = 0; a < array.length; a++) {
            if (array[a] <= -1000 || array[a] >= 1000) {
                System.out.println("Not withing limit.");
                return false;
            }
        }
        return true;
    }

    private void rotateMatrix(int rowLength, int colLength, int num, int[][] matrix) {
        if (limitCheck(matrix[num])) {
            int[] row = populateArray(matrix[num]);
            int colIndex = colLength - 1 - num;
            if (num + 1 < rowLength) rotateMatrix(rowLength, colLength, num + 1, matrix);
            fitRowAsColumn(row, colIndex, rowLength, matrix);
        }
    }

    private void rotate(int[][] matrix) {
        if (matrix.length == matrix[0].length && matrix.length > 0 && matrix.length < 21)
            rotateMatrix(matrix.length, matrix[0].length, 0, matrix);
    }

    private void displayMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        RotateImage rotateImage = new RotateImage();
//                int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
//        int[][] matrix = new int[][]{{1001, 0}, {5, 4}};
        rotateImage.displayMatrix(matrix);
        rotateImage.rotate(matrix);
        System.out.println();
        rotateImage.displayMatrix(matrix);
    }
}
