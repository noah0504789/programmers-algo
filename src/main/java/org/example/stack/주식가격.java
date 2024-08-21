package org.example.stack;

public class 주식가격 {
    public static void main(String[] args) {
        int[] solution = solution(new int[]{1, 2, 3, 2, 3});

        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
    }

    // prices
    // - n초간의 주가를 기록 (초 단위)

    // 가격이 떨어지지 않은 기간 (떨어지면 비교 X)
    public static int[] solution(int[] prices) {
        int[] answer = new int[prices.length];

        for (int pointInTime = 0; pointInTime < prices.length-1; pointInTime++) {
            // TODO: 부분집합 (시점 - 비교)
            int period = 0;
            for (int start = pointInTime+1; start < prices.length; start++) {
                if (prices[pointInTime] > prices[start]) break;

                period++;
            }

            if (period == 0) period = 1;

            // TODO: 시점별 떨어지지 않은 시간 값 할당
            answer[pointInTime] = period;
        }

        answer[prices.length-1] = 0;

        return answer;
    }

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

    // 마지막 주식 => 무조건 0
}
