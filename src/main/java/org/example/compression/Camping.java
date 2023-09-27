package org.example.compression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Camping {
    // 출처 : https://velog.io/@doforme/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%EC%9E%90%EB%B0%94-%EC%BA%A0%ED%95%91
    public int solution(int n, int[][] data) {
        int answer = 0;
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();

        for (int[] p : data) {
            xList.add(p[0]);
            yList.add(p[1]);
        }

        List<Integer> uniqueXList = new ArrayList<>(new HashSet<>(xList));
        List<Integer> uniqueYList = new ArrayList<>(new HashSet<>(yList));

        Collections.sort(uniqueXList);
        Collections.sort(uniqueYList);

        int[][] dp = new int[5000][5000];
        for (int i = 0; i < n; i++) {
            data[i][0] = uniqueXList.indexOf(xList.get(i));
            data[i][1] = uniqueYList.indexOf(yList.get(i));

            dp[data[i][1]][data[i][0]] = 1;
        }

        for (int r = 0; r < 5000; r++) {
            for (int c = 0; c < 5000; c++) {
                if (r-1 >= 0) {
                    dp[r][c] += dp[r-1][c];
                }

                if (c-1 >= 0) {
                    dp[r][c] += dp[r][c-1];
                }

                if (r-1 >= 0 && c-1 >= 0) {
                    dp[r][c] -= dp[r-1][c-1];
                }
            }
        }

        Arrays.sort(data, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int x1 = Math.min(data[i][0], data[j][0]);
                int y1 = Math.min(data[i][1], data[j][1]);
                int x2 = Math.max(data[i][0], data[j][0]);
                int y2 = Math.max(data[i][1], data[j][1]);

                if (x2 == x1 || y2 == y1) {
                    continue;
                }

                int count = dp[y2-1][x2-1] - dp[y2-1][x1] - dp[y1][x2-1] + dp[y1][x1];

                if (count == 0) {
                    answer++;
                }
            }

        }

        return answer;
    }
}
