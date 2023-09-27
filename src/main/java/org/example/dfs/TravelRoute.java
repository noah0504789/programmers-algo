package org.example.dfs;

public class TravelRoute {
    String result = "J";
    StringBuilder sb = new StringBuilder();
    String[][] tickets;
    boolean[] used;
    int size;
    // 출처 : https://school.programmers.co.kr/questions/29399
    public String[] solution(String[][] tickets) {
        this.tickets = tickets;
        size = tickets.length;
        used = new boolean[size];
        sb.append("ICN ");
        dfs(0, "ICN");

        return result.split(" ");
    }

    private void dfs(int count, String start) {
        if (count == size) {
            result = (result.compareTo(sb.toString()) > 0) ? sb.toString() : result;
            return;
        }

        for (int i = 0; i < size; i++) {
            if (!used[i] && tickets[i][0].equals(start)) {
                used[i] = true;
                sb.append(tickets[i][1]).append(" ");
                dfs(count+1, tickets[i][1]);
                sb.setLength(sb.length()-4);
                used[i] = false;
            }
        }
    }
}
