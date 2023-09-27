package org.example.dynamic;

public class Operator {

    public static int solution(String arr[]) {
        int answer = -1;
        int length = arr.length, operatorCnt = arr.length/2;
        int[][] dp1 = new int[length][length]; // 최대합
        int[][] dp2 = new int[length][length]; // 최소합
        int op1, op2, cal1, cal2;
        String operator;

        for (int i = 0; i < length; i+=2) {
            dp1[i][i] = Integer.parseInt(arr[i]);
            dp2[i][i] = Integer.parseInt(arr[i]);
        }

        for (int i = 1; i < length; i+=2) {
            op1 = dp1[i-1][i-1];
            op2 = dp1[i+1][i+1];
            operator = arr[i];

            dp1[i-1][i+1] = calculate(op1, op2, operator);
            dp2[i-1][i+1] = calculate(op1, op2, operator);
        }

        int k = 2;
        while (k <= operatorCnt) {
            for (int i = k; i < length-(k-1); i+=2) {
                int tmp = 2-k;
                op1 = dp1[i-k][i-k];
                op2 = dp1[i+tmp][i+k];
                operator = arr[i-(k-1)];
                cal1 = calculate(op1, op2, operator);

                op1 = dp1[i-k][i-tmp];
                op2 = dp1[i+k][i+k];
                operator = arr[i+(k-1)];
                cal2 = calculate(op1, op2, operator);

                dp1[i-k][i+k] = Math.max(cal1, cal2);
                dp2[i-k][i+k] = Math.min(cal1, cal2);
            }

            k++;
        }

        return dp1[0][length-1];
    }

    // 출처 : https://velog.io/@kamo0915/Java-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%EC%82%AC%EC%B9%99%EC%97%B0%EC%82%B0-4%EB%8B%A8%EA%B3%84
    int[][] min, max;
    public int solution2(String arr[]) {
        int size = arr.length/2+1;
        min = new int[size][size];
        max = new int[size][size];

        int[] list = new int[size];

        for (int i = 0; i < arr.length; i+=2) {
            int num = Integer.parseInt(arr[i]);
            if (i == 0) {
                list[i/2] = num;
            } else {
                list[i/2] = arr[i-1].equals("+") ? num : -num;
            }
        }

        for (int i = size-1; i >= 0; i--) {
            for (int j = i; j < size; j++) {
                if (i == j) {
                    min[i][j] = list[i];
                    max[i][j] = list[i];
                } else {
                    min[i][j] = Integer.MAX_VALUE;
                    max[i][j] = Integer.MIN_VALUE;

                    for (int k = i; k < j; k++) {
                        boolean value = k == i ? true : false;
                        func(min[i][k], min[k+1][j], i, j, value);
                        func(min[i][k], max[k+1][j], i, j, value);
                        func(max[i][k], min[k+1][j], i, j, value);
                        func(max[i][k], max[k+1][j], i, j, value);
                    }
                }
            }
        }
        return max[0][size-1];
    }

    public void func(int a, int b, int x, int y, boolean value) {
        if (value && a < 0) {
            min[x][y] = Math.min(min[x][y], Math.min(a-b,a+b));
            max[x][y] = Math.max(max[x][y], Math.max(a-b,a+b));
        } else {
            min[x][y] = Math.min(min[x][y], a+b);
            max[x][y] = Math.max(max[x][y], a+b);
        }
    }

    // 출처 : chatgpt
    public int solution3(String arr[]) {
        int size = arr.length/2+1;
        min = new int[size][size];
        max = new int[size][size];

        for (int i = 0; i < size; i++) {
            min[i][i] = Integer.parseInt(arr[i*2]);
            max[i][i] = Integer.parseInt(arr[i*2]);
        }

        for (int len = 2; len <= size; len++) {
            for (int i = 0; i <= size - len; i++) {
                int j = i + len - 1;
                min[i][j] = Integer.MAX_VALUE;
                max[i][j] = Integer.MIN_VALUE;
                for (int k = i; k < j; k++) {
                    if (arr[k*2+1].equals("+")) {
                        min[i][j] = Math.min(min[i][j], min[i][k] + min[k+1][j]);
                        max[i][j] = Math.max(max[i][j], max[i][k] + max[k+1][j]);
                    } else {
                        min[i][j] = Math.min(min[i][j], min[i][k] - max[k+1][j]);
                        max[i][j] = Math.max(max[i][j], max[i][k] - min[k+1][j]);
                    }
                }
            }
        }

        return max[0][size-1];
    }

    private static int calculate(int op1, int op2, String operator) {
        if (operator.equals("+")) {
            return op1 + op2;
        }

        return op1 - op2;
    }

    public static void main(String[] args) {
        String[] arr = {"1", "-", "3", "+", "5", "-", "8"};
        System.out.println(solution(arr));
    }
}
