package org.example.sort;

public class K번째수 {
    private int[] arr;

    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for (int i = 0; i < commands.length; i++) {
            int[] cmd = commands[i];
            int startIdx = cmd[0]-1, endIdx = cmd[1]-1, k = cmd[2];

            arr = array.clone();

//            System.out.println("startIdx: " + startIdx + " endIdx: " + endIdx + " k: " + k);

            for (int sortCnt = 0; sortCnt < k; sortCnt++) insertSort(startIdx + sortCnt, endIdx);

//            for (int idx = 0; idx < array.length; idx++) System.out.println(arr[idx]);

            answer[i] = arr[startIdx+k-1];

            System.out.println();
        }

        return answer;
    }

    private void insertSort(int startIdx, int endIdx) {
        for (int i = endIdx; i > startIdx; i--) {
            int srcIdx = i, targetIdx = i-1;

            if (arr[srcIdx] >= arr[targetIdx]) continue;

            swap(srcIdx, targetIdx);
        }
    }

    private void swap(int srcIdx, int targetIdx) {
        int temp = arr[targetIdx];
        arr[targetIdx] = arr[srcIdx];
        arr[srcIdx] = temp;
    }

    public static void main(String[] args) {
        K번째수 sol = new K번째수();

        for (int n : sol.solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}})) {
            System.out.println(n);
        }
    }
}
