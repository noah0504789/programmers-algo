package org.example.binarysearch;

import java.util.*;

// 문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/43238
public class Immigration {
    public long solution(int n, int[] times) {
        Arrays.sort(times);
        int count = 0;
        long minTime = 1, midTime = 0, maxTime = (long)times[0] * n;

        while (minTime <= maxTime) {
            midTime = (minTime + maxTime)/2;
            count = immigration(times, midTime, n);
            if (count == n) {
                maxTime = midTime-1;
            } else {
                minTime = midTime+1;
            }
        }

        return minTime;
    }

    private int immigration(int[] times, long time, int n) {
        int count = 0;
        for (int i = 0; i < times.length; i++) {
            count += time / times[i];
            if (count >= n) return n;
        }

        return count;
    }
}
