package com.shoppingService.LeetCode.Arrays.Problem_493.CountInversions;

import java.util.*;

class SampleSolution {

    // Function to merge two halves and count inversions
    private static int merge(int[] arr, int low, int mid, int high) {
        // Temporary array
        int[] temp = new int[high - low + 1];

        // Starting indices of left and right halves
        int left = low;
        int right = mid + 1;
        int k = 0;

        // Variable to count inversions
        int cnt = 0;

        // Merge elements in sorted order
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp[k++] = arr[left++];
            } else {
                temp[k++] = arr[right++];
                cnt += (mid - left + 1); // Count inversions
            }
        }

        // Copy remaining elements of left half
        while (left <= mid) {
            temp[k++] = arr[left++];
        }

        // Copy remaining elements of right half
        while (right <= high) {
            temp[k++] = arr[right++];
        }

        // Copy back to original array
        for (int i = low; i <= high; i++) {
            arr[i] = temp[i - low];
        }

        return cnt;
    }

    // Merge sort function
    private static int mergeSort(int[] arr, int low, int high) {
        int cnt = 0;

        if (low >= high) return cnt;

        int mid = (low + high) / 2;

        // Count inversions in left half
        cnt += mergeSort(arr, low, mid);
        // Count inversions in right half
        cnt += mergeSort(arr, mid + 1, high);
        // Count inversions during merge
        cnt += merge(arr, low, mid, high);

        return cnt;
    }

    // Function to get number of inversions
    private static int numberOfInversions(int[] arr) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        // Input array
        List<int[]> list = new ArrayList<>();
//        list.add(new int[]{1, 2, 3, 4, 5});
//        list.add(new int[]{5, 4, 3, 2, 1});
//        list.add(new int[]{5, 3, 2, 1, 4});
//        list.add(new int[]{5, 6, 2, 3});
        list.add(new int[]{2, 4, 8, 2, 4});
        for (int[] a : list) {
            // Create Solution object
            SampleSolution obj = new SampleSolution();
            int[] s = new int[a.length];
            for (int d = 0; d < s.length; d++) {
                s[d] = a[d];
            }
            // Count inversions
            int cnt = obj.numberOfInversions(a);

            System.out.println(Arrays.toString(s) + "\tThe number of inversions are: " + cnt);
        }
    }
}
