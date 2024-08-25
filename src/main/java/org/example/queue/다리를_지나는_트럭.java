package org.example.queue;

import java.util.*;

// return: 다리 건너는데 걸리는 최소 시간

// bridge_length: 다리에 올라갈 수 있는 트럭 수 (최대)
// weight: 다리가 견딜 수 있는 무게 (최대)
// - 무게 측정 기준: 트럭이 다리에 완전히 오름

// 트럭들이 일차선 다리를 건넘 (순서대로)
// 트럭은 단위시간당 단위거리를 이동할 수 있음 (1)

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

public class 다리를_지나는_트럭 {

    static Queue<Truck> trucksToWait, trucksOnBridge;
    static int capCount, capWeight, curBridgeWeight;

    public static void main(String[] args) {
        System.out.println(solution(2, 10, new int[]{7, 4, 5, 6}));
//        System.out.println(solution(100, 100, new int[]{10}));
//        System.out.println(solution(100, 100, new int[]{10,10,10,10,10,10,10,10,10,10}));
    }

    public static int solution(int bridge_length, int weight, int[] truck_weights) {
        int timeElapsed = 0;
        capCount = bridge_length;
        capWeight = weight;

        trucksToWait = new ArrayDeque<>(Arrays.stream(truck_weights).boxed().map(Truck::new).toList());
        trucksOnBridge = new ArrayDeque<>(capCount);
        curBridgeWeight = 0;

        while (allTrucksCrossedBridge()) {
            timeElapsed++;

            trucksOnBridge.forEach(Truck::move);

            while (canCrossTheBridge()) {
                Truck passedTruck = trucksOnBridge.poll();
                curBridgeWeight -= passedTruck.weight;
            }

            if (canAddTruckToBridge()) {
                Truck truckToEnter = trucksToWait.poll();
                curBridgeWeight += truckToEnter.weight;

                truckToEnter.move();
                trucksOnBridge.offer(truckToEnter);
            }
        }

        return timeElapsed;
    }

    private static boolean allTrucksCrossedBridge() {
        return !trucksToWait.isEmpty() || !trucksOnBridge.isEmpty();
    }

    private static boolean canCrossTheBridge() {
        return !trucksOnBridge.isEmpty() && 
                trucksOnBridge.peek().hasReachedEndOfBridge(capCount);
    }

    private static boolean canAddTruckToBridge() {
        return !trucksToWait.isEmpty() && 
                trucksOnBridge.size() < capCount && 
                curBridgeWeight + trucksToWait.peek().weight <= capWeight;
    }

    static class Truck {
        final int weight;
        int position;

        public Truck(int weight) {
            this.weight = weight;
        }

        public void move() {
            position++;
        }

        public boolean hasReachedEndOfBridge(int endOfBridge) {
            return position > endOfBridge;
        }
    }
}
