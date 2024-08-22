package org.example.queue;

import java.util.*;

public class �ٸ���_������_Ʈ�� {

    public static void main(String[] args) {
        System.out.println(solution(2, 10, new int[]{7, 4, 5, 6}));
    }

    // return: �ٸ� �ǳʴµ� �ɸ��� �ּ� �ð�

    // bridge_length: �ٸ��� �ö� �� �ִ� Ʈ�� �� (�ִ�)
    // weight: �ٸ��� �ߵ� �� �ִ� ���� (�ִ�)
    // - ���� ���� ����: Ʈ���� �ٸ��� ������ ����

    // Ʈ������ ������ �ٸ��� �ǳ� (�������)

    // bridge_length: 2
    // weight: 10
    // truck_weights: 7 4 5 6

    //                   (2)(10)
    // ���� | ��� Ʈ�� |    �ǳʴ� Ʈ��   |  �ǳ� Ʈ��
    //  0        X          X                X
    //  1      4 5 6      7 (�ö��)          X

    // ���� �ǳ� �� �ִ��� Ȯ�� (capacity check && weight check)
    //  2      4 5 6      7 (�Ѿ)          X
    //  3      5 6        4 (�ö��)          7

    // ���� �ǳ� �� �ִ��� Ȯ�� (capacity check && weight check)
    //  4       6       4 (�Ѿ) 5 (�ö��)  7

    // ���� �ǳ� �� �ִ��� Ȯ�� (capacity check && weight check)
    //  5       6          5 (�Ѿ)        7 4

    //  6       6          6 (�ö��)        7 4
    //  7       X          6 (�Ѿ)        7 4 5
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

            // TODO: Ʈ�� �ѱ�� (pass -> passed)
            if (!trucksOntoRoad.isEmpty() && canThroughBridge()) {
                trucksOntoRoad.stream().takeWhile(Truck::isPass).forEach(t -> trucksOntoRoad.poll());
            }
        }

        return time;
    }

    private static boolean canOntoRoad() {
        // TODO: Ʈ���� ���ο� �ö�� �� �ִ��� üũ (capWeight, capCount)
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
