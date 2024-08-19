package org.example.sort;

public class 가장_큰_수 {
    public static String solution(int[] numbers) {
        // (맨 앞자리 수가 같음 -> 작은 수의 맨 앞자리와 큰 수의 맨 뒷자리 수를 비교 -> 큰 수가 앞으로)
        // 맨 앞자리가 다름: 내림차순으로
        // 맨 앞자리가 같음
        // - 자릿수가 다른 경우: 뒷숫자로 판단해야 함

        // 3, 30, 34, 5, 9

        // 9 5
        // 3, 30, 34 -> 34 3 30

        // 34 30 3: 34303
        // 34 3 30: 34330

        // 3 30 34: 33034
        // 3 34 30: 33430

        // 30 34 3: 30343
        // 30 3 34: 30334

        // 9 5 34 3 30

        // 3 30 34 => 한번씩 써서 가장 큰 수 만들기
        // 34 3 30
        //

        String answer = "";

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{6, 10, 2}));
        System.out.println(solution(new int[]{3, 30, 34, 5, 9}));
    }
}

