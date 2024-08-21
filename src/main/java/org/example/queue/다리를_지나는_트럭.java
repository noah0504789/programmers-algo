package org.example.queue;

public class 다리를_지나는_트럭 {

    public static void main(String[] args) {

    }

    // 트럭들이 일차선 다리를 건넘 (순서대로)
    // 다리 건너는데 걸리는 시간 (최소)

    // bridge_length: 다리에 올라갈 수 있는 트럭 수 (최대)
    // weight: 다리가 견딜 수 있는 무게 (최대)
    // - 무게 측정 기준: 트럭이 다리에 완전히 오름

    // bridge_length: 2
    // weight: 10
    // truck_weights: 7 4 5 6

    //          (2)
    // 시점 | 건너는 트럭 | 대기 트럭 |  건넌 트럭
    //  0        X          X           X
    //  1        7        4 5 6         X
    //  2        7        4 5 6         X

    public static int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        return answer;
    }
}
