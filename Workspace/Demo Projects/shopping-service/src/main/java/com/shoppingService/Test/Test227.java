package com.shoppingService.Test;

import java.util.HashSet;
import java.util.Set;

public class Test227 {
    private static void setZeroes(int[][] matrix) {
        Set<Integer> rowSet = new HashSet<>();
        Set<Integer> colSet = new HashSet<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (0 == matrix[row][col]) {
                    rowSet.add(row);
                    colSet.add(col);
                }
            }
        }
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (rowSet.contains(row) || colSet.contains(col)) matrix[row][col] = 0;
            }
        }

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
//        int[][] matrix = new int[][]{{3, 1, 2}, {3, 0, 5}, {1, 3, 2}};
//        int[][] matrix = new int[][]{{0, 1, 2, 4}, {3, 8, 5, 4}, {1, 3, 2, 4}, {1, 3, 2, 0}};
//        int[][] matrix = new int[][]{{3, 1, 2, 4}, {3, 8, 0, 4}, {1, 3, 2, 4}, {1, 3, 2, 4}};
//        int[][] matrix = new int[][]{{0, 1}};
//        int[][] matrix = new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        int[][] matrix = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        displayMatrix(matrix);
        setZeroes(matrix);
        System.out.println();
        displayMatrix(matrix);
    }
}
