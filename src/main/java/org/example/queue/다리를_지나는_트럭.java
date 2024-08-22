package org.example.queue;

import java.util.*;

public class 다리를_지나는_트럭 {

    public static void main(String[] args) {
        System.out.println(solution(2, 10, new int[]{7, 4, 5, 6}));
    }

    // return: 다리 건너는데 걸리는 최소 시간

    // bridge_length: 다리에 올라갈 수 있는 트럭 수 (최대)
    // weight: 다리가 견딜 수 있는 무게 (최대)
    // - 무게 측정 기준: 트럭이 다리에 완전히 오름

    // 트럭들이 일차선 다리를 건넘 (순서대로)

    // bridge_length: 2
    // weight: 10
    // truck_weights: 7 4 5 6

    //                   (2)(10)
    // 시점 | 대기 트럭 |    건너는 트럭   |  건넌 트럭
    //  0        X          X                X
    //  1      4 5 6      7 (올라옴)          X

    // 같이 건널 수 있는지 확인 (capacity check && weight check)
    //  2      4 5 6      7 (넘어감)          X
    //  3      5 6        4 (올라옴)          7

    // 같이 건널 수 있는지 확인 (capacity check && weight check)
    //  4       6       4 (넘어감) 5 (올라옴)  7

    // 같이 건널 수 있는지 확인 (capacity check && weight check)
    //  5       6          5 (넘어감)        7 4

    //  6       6          6 (올라옴)        7 4
    //  7       X          6 (넘어감)        7 4 5
    //  8       X          X                7 4 5 6

    static int capCount, capWeight, curSumOfOntoRoadTruckWeight;
    static Queue<Truck> trucksOntoRoad, trucksToWait;

    public static int solution(int bridge_length, int weight, int[] truck_weights) {
        int time = 0;

        int capCount = bridge_length, capWeight = weight;
        trucksToWait = new ArrayDeque<>(Arrays.stream(truck_weights).boxed().map(Truck::new).toList());
        trucksOntoRoad = new ArrayDeque<>(capCount);
        curSumOfOntoRoadTruckWeight = 0;

        while (true) {
            time++;

            if (trucksToWait.isEmpty() && trucksOntoRoad.isEmpty()) break;

            while (!trucksToWait.isEmpty() && canOntoRoad()) {
                curSumOfOntoRoadTruckWeight += trucksToWait.peek().weight;
                trucksOntoRoad.offer(trucksToWait.poll());
                trucksOntoRoad.peek().ontoRoad();
            }

            if (!trucksOntoRoad.isEmpty() && canPass()) trucksOntoRoad.stream().takeWhile(Truck::isOntoRoad).forEach(Truck::pass);

            // TODO: 트럭 넘기기 (pass -> passed)
            if (!trucksOntoRoad.isEmpty() && canThroughBridge()) {
                trucksOntoRoad.stream().takeWhile(Truck::isPass).forEach(t -> trucksOntoRoad.poll());
            }
        }

        return time;
    }

    private static boolean canOntoRoad() {
        // TODO: 트럭이 도로에 올라올 수 있는지 체크 (capWeight, capCount)
        // 1. capWeight
        Truck peekTruck = trucksToWait.peek();
        if (curSumOfOntoRoadTruckWeight + peekTruck.weight > capWeight) return false;

        // 2. capCount
        if (trucksOntoRoad.size() >= capCount) return false;

        return true;
    }

    private static boolean canPass() {
        if (trucksOntoRoad.peek().isOntoRoad()) return true;

        return false;
    }

    private static boolean canThroughBridge() {
        if (trucksOntoRoad.peek().isPass()) return true;

        return false;
    }

    static class Truck {
        private static final int WAITING = -100;
        private static final int ONTO_ROAD = 100;
        private static final int PASS = 200;
        int status, weight;

        public Truck(int weight) {
            this.weight = weight;
            this.status = WAITING;
        }

        public void ontoRoad() {
            this.status = ONTO_ROAD;
        }

        public void pass()  {
            this.status = PASS;
        }

        public boolean isWaiting() {
            return this.status == WAITING;
        }

        public boolean isOntoRoad() {
            return this.status == ONTO_ROAD;
        }

        public boolean isPass() {
            return this.status == PASS;
        }
    }
}
