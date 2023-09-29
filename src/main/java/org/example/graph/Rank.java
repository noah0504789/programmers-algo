package org.example.graph;

// 문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/49191
public class Rank {
    public int solution(int n, int[][] results) {
        int answer = 0;
        boolean[][] graph = new boolean[n][n];

        for (int i = 0; i < results.length; i++) {
            graph[results[i][0]-1][results[i][1]-1] = true;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (graph[i][k] && graph[k][j]) graph[i][j] = true;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (graph[i][j] || graph[j][i]) count++;
            }
            if (count == n-1) answer++;
        }

        return answer;
    }
}
