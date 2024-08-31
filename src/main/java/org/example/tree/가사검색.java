package org.example.tree;

import java.util.*;

public class 가사검색 {
    private static Trie[] tries, reversedTries;
    private static StringBuilder sb;

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[]{"frodo", "front", "frost", "frozen", "frame", "kakao"}, new String[]{"fro??", "????o", "fr???", "fro???", "pro?"})));
    }

    public static int[] solution(String[] words, String[] queries) {
        int[] ans = new int[queries.length];

        tries = new Trie[10001];
        reversedTries = new Trie[10001];

        sb = new StringBuilder();

        for (int i = 0; i < tries.length; i++) {
            tries[i] = new Trie();
            reversedTries[i] = new Trie();
        }

        for (String word : words) {
            int length = word.length();
            tries[length].insert(word);

            sb.setLength(0);
            reversedTries[length].insert(sb.append(word).reverse().toString());
        }

        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            int length = query.length();
            int cnt = 0;

            if (query.charAt(0) == '?' && query.charAt(length-1) == '?') cnt = tries[length].totalCnt;
            else if (query.charAt(length-1) == '?') cnt = tries[length].countWord(query.replace("?", ""));
            else if (query.charAt(0) == '?') {
                sb.setLength(0);
                cnt = reversedTries[length].countWord(sb.append(query.replace("?", "")).reverse().toString());
            }

            ans[i] = cnt;
        }

        return ans;
    }

    static class Trie {
        class Node {
            Map<Character, Node> children;
            int count;

            public Node() {
                children = new HashMap<>();
                count = 0;
            }
        }

        Node root;
        int totalCnt;

        public Trie() {
            root = new Node();
            totalCnt = 0;
        }

        public void insert(String word) {
            Node node = this.root;
            for (char c : word.toCharArray()) {
                node = node.children.computeIfAbsent(c, k -> new Node());
                node.count++;
            }
            totalCnt++;

//            insert(this.root, word, 0);
//            totalCnt++;
        }

        public int countWord(String prefix) {
            Node node = this.root;
            for (char c : prefix.toCharArray()) {
                if (!node.children.containsKey(c)) return 0;
                node = node.children.get(c);
            }
            return node.count;

//            return countWord(this.root, prefix, 0);
        }

        private void insert(Node cur, String word, int idx) {
            if (word.length() == idx) {
                cur.count++;
                return;
            }

            char c = word.charAt(idx);

            if (!cur.children.containsKey(c)) cur.children.put(c, new Node());

            insert(cur.children.get(c), word, idx+1);
        }

        private int countWord(Node cur, String prefix, int idx) {
            if (prefix.length() == idx) {
                return countAllWord(cur);
            }

            char c = prefix.charAt(idx);

            if (!cur.children.containsKey(c)) return 0;

            return countWord(cur.children.get(c), prefix, idx+1);
        }

        private int countAllWord(Node cur) {
            int cnt = cur.count;
            for (Node child : cur.children.values()) cnt += countAllWord(child);

            return cnt;
        }
    }
}
