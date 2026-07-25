package com.shoppingService.LeetCode.Arrays.Problem_73;

import java.util.ArrayList;
import java.util.List;

public class SetMatrixToZero3 {

    public static void main(String[] args) {
        SetMatrixToZero3 setMatrixToZero = new SetMatrixToZero3();
        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{3, 1, 2}, {3, 0, 5}, {1, 3, 2}});
        list.add(new int[][]{{0, 1, 2, 4}, {3, 8, 5, 4}, {1, 3, 2, 4}, {1, 3, 2, 0}});
        list.add(new int[][]{{3, 1, 2, 4}, {3, 8, 0, 4}, {1, 3, 2, 4}, {1, 3, 2, 4}});
        list.add(new int[][]{{0, 1}});
        list.add(new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}});
        list.add(new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}});
        for (int[][] matrix : list) {
            setMatrixToZero.displayMatrix(matrix);
            setMatrixToZero.setZeroes(matrix.length, matrix[0].length, matrix);
            System.out.println();
            setMatrixToZero.displayMatrix(matrix);
            System.out.println();
        }
    }

    private void setZeroes(int m, int n, int[][] matrix) {
        boolean firstRowZero = false;
        boolean firstColZero = false;

        // Step 1: Check first row and column
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) firstRowZero = true;
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) firstColZero = true;
        }

        // Step 2: Use first row/col as markers
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // Step 3: Update cells based on markers
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 4: Handle first row and column
        if (firstRowZero) {
            for (int j = 0; j < n; j++) matrix[0][j] = 0;
        }
        if (firstColZero) {
            for (int i = 0; i < m; i++) matrix[i][0] = 0;
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
}
