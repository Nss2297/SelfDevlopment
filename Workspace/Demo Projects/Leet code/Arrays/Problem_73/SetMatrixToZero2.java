package com.shoppingService.LeetCode.Arrays.Problem_73;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetMatrixToZero2 {

    private void setZeroes(int[][] matrix) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    rows.add(row);
                    cols.add(col);
                }
            }
        }
        for (int row = 0; row < rows.size(); row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[rows.get(row)][col] = 0;
            }
        }
        for (int a = 0; a < cols.size(); a++) {
            for (int col = 0; col < matrix.length; col++) {
                matrix[col][cols.get(a)] = 0;
            }
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
        SetMatrixToZero2 setMatrixToZero = new SetMatrixToZero2();
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
