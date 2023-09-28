package org.example.graph;

import java.util.*;

// 문제출처 : https://school.programmers.co.kr/learn/courses/30/lessons/49189
public class LongestNode {
    public int solution(int n, int[][] edge) {
        int answer = 0, longestDist = 0;
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Set<Integer>> edges = init(edge, new HashMap<>());

        boolean[] visited = new boolean[n];
        visited[0] = true;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0, 0});

        while (!queue.isEmpty()) {
            int[] c = queue.poll();
            int cp = c[0], ct = c[1];

            for (int neignbor : edges.get(cp)) {
                if (visited[neignbor]) continue;
                int np = neignbor, nt = ct+1;
                if (!map.containsKey(nt)) map.put(nt, 1);
                else map.put(nt, map.get(nt)+1);

                longestDist = nt;

                visited[neignbor] = true;
                queue.offer(new int[] {np, nt});
            }
        }

        return map.get(longestDist);
    }

    private Map<Integer, Set<Integer>> init(int[][] edge, Map<Integer, Set<Integer>> edges) {
        for (int i = 0; i < edge.length; i++) {
            int src = edge[i][0]-1, desc = edge[i][1]-1;
            makeEdges(src, desc, edges);
            makeEdges(desc, src, edges);
        }

        return edges;
    }

    private void makeEdges(int src, int desc, Map<Integer, Set<Integer>> edges) {
        if (!edges.containsKey(src)) {
            Set<Integer> set = new HashSet<>();
            set.add(desc);
            edges.put(src, set);
        } else {
            edges.get(src).add(desc);
        }
    }
}
