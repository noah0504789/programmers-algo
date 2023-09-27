package org.example.dynamic;

import java.util.HashSet;
import java.util.Set;

public class CountDown {
    // 출처 : programmers (공유하면 안됨)
    public int[] solution(int target) {
        int[][] memo = new int[target+1][2];
        Set<Integer> set = new HashSet<>();
        set.add(50);

        for (int i = 1; i <= 20; i++) {
            set.add(i);
            set.add(i*2);
            set.add(i*3);
        }

        for (int i = 1; i<= target && i <= 60; i++) {
            if (set.contains(i)) {
                memo[i][0] = 1;
                if (i == 50 || i <= 20) {
                    memo[i][1] = 1;
                }
            } else {
                if (i > 50 || i <= 40) {
                    memo[i][0] = 2;
                    memo[i][1] = 2;
                } else {
                    memo[i][0] = 2;
                    memo[i][1] = 1;
                }
            }
        }

        if (target > 60) {
            for (int i = 61; i <= target; i++) {
                if (memo[i-50][0] <= memo[i-60][0]) {
                    memo[i][0] = memo[i-50][0] + 1;
                    memo[i][1] = memo[i-50][1] + 1;
                } else {
                    memo[i][0] = memo[i-60][0] + 1;
                    memo[i][1] = memo[i-60][1];
                }
            }
        }

        return memo[target];
    }
}
