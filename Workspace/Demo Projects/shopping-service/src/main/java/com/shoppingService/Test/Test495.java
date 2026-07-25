package com.shoppingService.Test;

public class Test495 {
    private static void fitRowInCol(int rowLength, int[] row, int colIndex, int[][] matrix) {
        for (int a = 0; a < rowLength; a++) {
            matrix[a][colIndex] = row[a];
        }
    }

    private static int[] populateArray(int size, int[] row) {
        int[] array = new int[size];
        for (int a = 0; a < size; a++) {
            array[a] = row[a];
        }
        return array;
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

    private static void rotateImage(int rowLength, int colLength, int num, int[][] matrix) {
        if (limitCheck(matrix[num])) {
            int[] row = populateArray(matrix[num].length, matrix[num]);
            int colIndex = colLength - 1 - num;
            if (num + 1 < rowLength) rotateImage(rowLength, colLength, num + 1, matrix);
            fitRowInCol(rowLength, row, colIndex, matrix);
        }
    }

    private static void rotate(int[][] matrix) {
        int rowLength = matrix.length;
        if (rowLength == matrix[0].length && rowLength < 21)
            rotateImage(rowLength, matrix[0].length, 0, matrix);
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
