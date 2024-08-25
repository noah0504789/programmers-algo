package org.example.stack;

import java.util.Stack;

public class 주식가격 {
    public static void main(String[] args) {
//        int[] solution = solution(new int[]{1, 2, 3, 2, 3});
        int[] solution = solution(new int[]{1, 2, 3, 2, 3, 3, 1});

        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
    }

    // prices
    // - 가격이 떨어지지 않은 기간 (떨어지면 비교 X)
    // - n초간의 주가를 기록 (초 단위)

    // 첫번쨰 주식 관점 <1> (0) (1, 2, 3, 4)
    // prices 2 3 2 3
    //  1(2s) X
    //  2(3s) X X
    //  3(4s) X X X
    //  3(4s) X X X X
    // => 4

    // 두번쨰 주식 관점 <2> (1) (2, 3, 4)
    // prices 3 2 3
    //  2(3s) X
    //  3(4s) X X
    //  3(4s) X X X
    // => 3

    // 세번쨰 주식 관점 <3> (2) (3, 4)
    // prices 2 3
    //  3(4s) 1(O) (4에 떨어지므로 3~3.99999 -> 1로 봄)
    //  3(4s) X
    // => 1

    // 네번쨰 주식 관점 <2> (3) (4)
    // prices 3
    //  3(4s) X
    // => 1

    public static int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[i] < prices[stack.peek()]) {
                int idx = stack.pop();
                answer[idx] = i - idx;
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int idx = stack.pop();
            answer[idx] = prices.length-1 - idx;
        }

        return answer;
    }

    public static int[] solution2(int[] prices) {
        int[] answer = new int[prices.length];

        for (int pointInTime = 0; pointInTime < prices.length-1; pointInTime++) {
            int period = 0;
            for (int start = pointInTime+1; start < prices.length; start++) {
                period++;

                if (prices[pointInTime] > prices[start]) break;
            }

            answer[pointInTime] = period;
        }

        answer[prices.length-1] = 0;

        return answer;
    }
}
