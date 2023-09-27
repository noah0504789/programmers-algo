package org.example.dynamic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Puddle {

    class Coordinate {
        public int y, x;

        public Coordinate(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int hashCode() {
            return this.y*200 + this.x;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (this.getClass() != obj.getClass()) {
                return false;
            }

            Coordinate other = (Coordinate) obj;
            return this.y == other.y && this.x == other.x;
        }
    }

    int[] dy = {0,1}; // 오른 아래
    int[] dx = {1,0}; // 오른 아래
    int[][] dp, puddles2;
    Set<Coordinate> visited;

    public int solution(int m, int n, int[][] puddles) {
        dp = new int[n][m];
        dp[0][0] = 1;
        puddles2 = puddles;

        Queue<Coordinate> queue = new LinkedList<>();
        visited = new HashSet<>();

        Coordinate c1 = new Coordinate(0, 0);
        queue.offer(c1);

        while (!queue.isEmpty()) {
            Coordinate c = queue.poll();
            // visited.add(c);
            int cy = c.y;
            int cx = c.x;

            if (cy == n-1 && cx == m-1) {
                break;
            }

            for (int i = 0; i < 2; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                Coordinate nc = new Coordinate(ny, nx);
                // System.out.println("ny : " + ny + " || nx : " + nx);

                if (validate(nc, n, m)) {
                    queue.offer(nc);
                    dp[ny][nx] = turn(ny, nx);
                    // System.out.println("ny : " + ny + " || nx : " + nx + " || dp : " + dp[ny][nx]);
                }
            }
        }

        return dp[n-2][m-1] + dp[n-1][m-2];
    }

    private boolean validate(Coordinate c, int n, int m) {
        // if (visited.contains(c)) {
        //     return false;
        // }

        if (c.y < 0 || c.y >= n || c.x < 0 || c.x >= m) {
            return false;
        }

        for (int[] p : puddles2) {
            int py = p[1];
            int px = p[0];

            if (c.y == py-1 && c.x == px-1) {
                return false;
            }
        }

        return true;
    }

    private int turn(int ny, int nx) {
        int turn = 0;
        if (ny-1 >= 0 && nx >= 0) {
            turn += dp[ny-1][nx];
        }

        if (ny >= 0 && nx-1 >= 0) {
            turn += dp[ny][nx-1];
        }

        if (turn > 1000000007) {
            turn %= 1000000007;
        }

        return turn;
    }
}
