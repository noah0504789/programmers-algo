package org.example.sort;

import java.util.Arrays;
import java.util.stream.Stream;

public class 가장_큰_수 {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{6, 10, 2}));
        System.out.println(solution(new int[]{3, 30, 34, 5, 9}));
    }

    public static String solution(int[] numbers) {
        if (numbers.length == 0) return "";

        String[] arr = Arrays.stream(numbers).mapToObj(String::valueOf).toArray(String[]::new);

        Arrays.sort(arr, (a, b) -> (b+a).compareTo(a+b));

        String ans = String.join("", arr);

        int startIdx = 0, endIdx = ans.length();
        while (ans.length() > 1 && ans.startsWith("0")) {
            if (endIdx < 2) break;

            endIdx--;
        }

        return ans.substring(startIdx, endIdx);
    }
}

