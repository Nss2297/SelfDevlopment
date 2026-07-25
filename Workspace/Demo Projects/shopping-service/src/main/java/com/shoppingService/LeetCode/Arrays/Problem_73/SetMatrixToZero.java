package com.shoppingService.LeetCode.Arrays.Problem_73;

import java.util.HashSet;
import java.util.Set;

public class SetMatrixToZero {
//    private void setZerosForColumns(Set<Integer> cols, int[][] matrix) {
//        for (int row = 0; row < matrix.length; row++) {
//            for (int col = 0; col < matrix[row].length; col++) {
//                if (cols.contains(col) && matrix[row][col] != 0) {
//                    matrix[row][col] = 0;
//                }
//            }
//        }
//    }

    private void setZerosForRow(Set<Integer> rows, int[][] matrix) {
//        rows.forEach(row -> {
//            for (int col = 0; col < matrix[row].length; col++) {
//                if (matrix[row][col] != 0) matrix[row][col] = 0;
//            }
//        });
        for (int row = 0; row < matrix.length; row++) {
            if (rows.contains(row)) {
                for (int col = 0; col < matrix[row].length; col++) {
                    if (0 != matrix[row][col]) matrix[row][col] = 0;
                }
            }
        }
    }

    private void populateRowAndColSet(Set<Integer> rows, Set<Integer> cols, int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                int num = matrix[row][col];
                if (num == 0 && num < Integer.MAX_VALUE - 1 && num >= Integer.MIN_VALUE) {
                    rows.add(row);
                    cols.add(col);
                }
                if (0 != num && cols.contains(col)) matrix[row][col] = 0;
            }
            if (rows.contains(row)) setZerosForRow(rows, matrix);
        }
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (0 != matrix[row][col] && cols.contains(col)) matrix[row][col] = 0;
            }
        }
    }

    private void setZeroes(int[][] matrix) {
        if (matrix.length > 0) {
            Set<Integer> rows = new HashSet<>();
            Set<Integer> cols = new HashSet<>();
            populateRowAndColSet(rows, cols, matrix);
        }
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
        SetMatrixToZero setMatrixToZero = new SetMatrixToZero();
        int[][] matrix = new int[][]{{3, 1, 2}, {3, 0, 5}, {1, 3, 2}};
//        int[][] matrix = new int[][]{{0, 1, 2, 4}, {3, 8, 5, 4}, {1, 3, 2, 4}, {1, 3, 2, 0}};
//        int[][] matrix = new int[][]{{3, 1, 2, 4}, {3, 8, 0, 4}, {1, 3, 2, 4}, {1, 3, 2, 4}};
//        int[][] matrix = new int[][]{{0, 1}};
//        int[][] matrix = new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
//        int[][] matrix = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        setMatrixToZero.displayMatrix(matrix);
        setMatrixToZero.setZeroes(matrix);
        System.out.println();
        setMatrixToZero.displayMatrix(matrix);
    }
}
