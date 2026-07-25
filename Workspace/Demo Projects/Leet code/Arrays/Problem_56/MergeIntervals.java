package com.shoppingService.LeetCode.Arrays.Problem_56;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MergeIntervals {
    public static void main(String[] args) {
        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}});
        list.add(new int[][]{{4, 7}, {1, 4}});
        list.add(new int[][]{{1, 4}, {4, 5}});
        list.add(new int[][]{{1, 3}});
        list.add(new int[][]{{1, 4}, {1, 5}});
        list.add(new int[][]{{1, 4}, {2, 5}});
        list.add(new int[][]{{1, 4}, {5, 6}});
        list.add(new int[][]{{1, 4}, {2, 3}});
        list.add(new int[][]{{1, 4}, {0, 2}, {3, 5}});
        for (int[][] matrix : list) {
            displayMatrix(matrix);
            quickSort(0, matrix.length - 1, matrix);
            displayMatrix(matrix);
            displayMatrix(merge(matrix));
            System.out.println();
        }
    }

    private static void quickSort(int low, int high, int[][] matrix) {
        if (low < high) {
            int pivotIndex = partition(low, high, matrix);
            quickSort(low, pivotIndex - 1, matrix);
            quickSort(pivotIndex + 1, high, matrix);
        }
    }

    private static int partition(int low, int high, int[][] matrix) {
        int pnt = low - 1;
        for (int a = low; a <= high; a++) {
            int num1 = matrix[a][0];
            int num2 = matrix[high][0];
            if (num1 == num2) {
                num1 = matrix[a][1];
                num2 = matrix[high][1];
            }
            if (num1 < num2) {
                ++pnt;
                swap(pnt, a, matrix);
            }
        }
        swap(pnt + 1, high, matrix);
        return ++pnt;
    }

    private static void swap(int num1Index, int num2Index, int[][] matrix) {
        int[] temp = matrix[num1Index];
        matrix[num1Index] = matrix[num2Index];
        matrix[num2Index] = temp;
    }

    private static void displayMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            System.out.print(Arrays.toString(matrix[row]) + "\t");
        }
        System.out.println();
    }

    private static int[][] merge(int[][] intervals) {
        if (intervals.length == 1) return intervals;
        List<int[]> list = new ArrayList<>();
            if (list.isEmpty()) list.add(intervals[0]);
        for (int a = 1; a < intervals.length; a++) {
            int[] firstArray = list.getLast();
            if (((intervals[a][0] == firstArray[1]) || (intervals[a][0] < firstArray[1]))) {
                int num2 = firstArray[1] > intervals[a][1] ? firstArray[1] : intervals[a][1];
                if (!list.isEmpty()) {
                    list.set(list.size() - 1, new int[]{firstArray[0], num2});
                } else {
                    list.add(new int[]{firstArray[0], num2});
                }
            } else list.add(new int[]{intervals[a][0], intervals[a][1]});
        }
        return list.toArray(new int[list.size()][]);
    }
}
