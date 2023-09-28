package org.example.binarysearch;

import java.util.*;

// 문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/43236
public class SteppingStone {
    public int solution(int distance, int[] rocks, int n) {
        long left = 1, right = distance, mid = 0, answer = 0, prevRock = 0, rockCount = 0;
        Arrays.sort(rocks);

        while (left <= right) {
            mid = (right + left)/2;
            prevRock = 0;
            rockCount = 0;

            for (int rock : rocks) {
                if (mid > rock - prevRock) rockCount++;
                else prevRock = rock;
            }

            if (mid > distance - prevRock) rockCount++;

            if (n >= rockCount) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (int) answer;
    }
}
