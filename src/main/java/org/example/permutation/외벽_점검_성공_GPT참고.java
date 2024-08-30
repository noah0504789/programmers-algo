package org.example.permutation;

import java.util.*;

public class 외벽_점검_성공_GPT참고 {
    private static int[] extendedWall, friends;
    private static int wallCnt, wallLength, friendCnt, ans;
    private static BitSet visited;

    public static void main(String[] args) {
        System.out.println(solution(12, new int[]{1, 5, 6, 10}, new int[]{1, 2, 3, 4}));
//        System.out.println(solution(12, new int[]{1, 3, 4, 9, 10}, new int[]{3, 5, 7}));
    }

    private static int solution(int n, int[] weak, int[] dist) {
        wallLength = n;
        wallCnt = weak.length;
        friendCnt = dist.length;
        ans = friendCnt + 1;

        extendedWall = new int[wallCnt * 2];

        for (int i = 0; i < wallCnt; i++) {
            extendedWall[i] = weak[i];
            extendedWall[i+wallCnt] = extendedWall[i] + wallLength;
        }

        friends = dist;
        visited = new BitSet(wallCnt);

        Arrays.sort(friends);

        do {
            for (int start = 0; start < wallCnt; start++) {
                visited.clear();
                int cnt = 0, pos = start;

                for (int friend = 0; friend < friendCnt; friend++) {
                    if (isClear()) break;

                    pos = clean(pos, friend);
                    if (pos == -1) break;

                    cnt++;
                }

                if (isClear()) ans = Math.min(ans, cnt);
            }
        } while (np());

        return ans > friendCnt ? -1 : ans;
    }

    private static boolean isClear() {
        return visited.cardinality() == wallCnt;
    }

    private static int clean(int start, int friend) {
        int startPos = extendedWall[start], endPos = startPos + friends[friend];

        while (startPos <= endPos) {
            visited.set(start);
            start++;

            if (start == extendedWall.length) return -1;

            startPos = extendedWall[start];
        }

        return start;
    }

    private static boolean np() {
        int i = friendCnt - 1;
        while (i > 0 && friends[i-1] >= friends[i]) i--;

        if (i == 0) return false;

        int j = friendCnt - 1;
        while (friends[i-1] >= friends[j]) j--;

        swap(i-1, j);

        int k = friendCnt - 1;
        while (i < k) swap(i++, k--);

        return true;
    }

    private static void swap(int src, int dest) {
        int tmp = friends[src];
        friends[src] = friends[dest];
        friends[dest] = tmp;
    }
}

//3, 4, 13, 23, 24
