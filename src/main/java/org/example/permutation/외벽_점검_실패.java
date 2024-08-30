package org.example.permutation;

import java.util.BitSet;

public class 외벽_점검_실패 {
    private static final int CLOCKWISE = 0, COUNTERCLOCKWISE = 1;
    private static int wallCnt, workerCnt;
    private static int[] workers;
    private static BitSet workerStatus;
    private static CircularQueue wall;

    public static void main(String[] args) {
        System.out.println(solution(12, new int[]{1, 5, 6, 10}, new int[]{1, 2, 3, 4}));
    }

    public static int solution(int n, int[] weak, int[] dist) {
        wallCnt = weak.length;
        workerCnt = dist.length;
        workerStatus = new BitSet(workerCnt);

        workers = dist;
        wall = new CircularQueue(n);

        for (int idx : weak) wall.mark(idx);

        return dfs(0);
    }

    private static int dfs(int cnt) {
        if (wall.isClear()) return cnt == workerCnt + 1 ? -1 : cnt;
        if (cnt >= workerCnt) return -1;

        int minCnt = workerCnt+1;

        for (int wIdx = 0; wIdx < workerCnt; wIdx++) {
            if (workerStatus.get(wIdx)) continue;

            workerStatus.set(wIdx);

            for (int j = 0; j < wallCnt; j++) {
                if (wall.isClean(j)) continue;

                for (int i = 0; i < 2; i++) {
                    if (i == CLOCKWISE) wall.cleanClockwise(j, workers[wIdx]);
                    else wall.cleanCounterClockwise(j, workers[wIdx]);

                    minCnt = Math.min(minCnt, dfs(cnt+1));

                    wall.undo();
                }
            }

            workerStatus.clear(wIdx);
        }

        return minCnt;
    }

    static class CircularQueue {
        BitSet visited, temp;
        final int size;

        public CircularQueue(int size) {
            this.size = size;
            this.visited = new BitSet(size);
            this.temp = new BitSet(size);
        }

        public void mark(int idx) {
            visited.set(idx);
        }

        // exclusive
        public void cleanClockwise(int fromIdx, int length) {
            copy();

            int idx = fromIdx;
            for (int i = 0; i <= length; i++) {
                if (idx == size) idx = 0;
                visited.clear(idx);
                idx++;
            }
        }

        public void cleanCounterClockwise(int fromIdx, int length) {
            copy();

            int idx = fromIdx;
            for (int i = 0; i <= length; i++) {
                if (idx == -1) idx = size-1;
                visited.clear(idx);
                idx--;
            }
        }

        public boolean isClear() {
            return visited.isEmpty();
        }

        public boolean isClean(int wallIdx) {
            return !visited.get(wallIdx);
        }

        public void undo() {
            paste();
        }

        public void copy() {
            temp.clear();
            temp.or(visited);
        }

        public void paste() {
            visited.clear();
            visited.or(temp);
        }
    }
}

// wall   [1, 5, 6, 10]
// worker [1, 2, 3, 4]

// 특정 벽을 시작
// 특정 일꾼 배치

// 0번 벽 - 0번 일꾼
// 0번 벽 - 1번 일꾼
// 0번 벽 - 2번 일꾼
// 0번 벽 - 3번 일꾼

//    private static int[][] dp; // worker status, wall status, if clockwise
