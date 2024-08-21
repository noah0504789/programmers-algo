package org.example.queue;

import java.util.BitSet;

public class 프로세스 {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{2, 1, 3, 2}, 2));
//        System.out.println(solution(new int[]{1, 1, 9, 1, 1, 1}, 0));
//        System.out.println(solution(new int[]{5, 4, 3, 2, 1}, 4));
//        System.out.println(solution(new int[]{1,2,3,4,5}, 2));
//        System.out.println(solution(new int[]{5, 4, 3, 2, 1}, 0));
//        System.out.println(solution(new int[]{2, 3, 3, 2, 9, 3, 3}, 3));
//        System.out.println(solution(new int[]{1}, 0));
    }

    // location: idx
    public static int solution(int[] priorities, int location) {
        if (priorities.length == 1) return 1;

        QueueWithPriority queue = new QueueWithPriority(priorities.length, location);

        for (int priority : priorities) queue.enqueue(priority);

        do {
            queue.dequeue();
        } while(!queue.isFind());

        return queue.getDequeueCnt();
    }

    static class QueueWithPriority {
        final int size;
        int[] priorities;
        int front, rear;
        BitSet enqueued;
        private final int idxToFind;
        private boolean isFind;
        int cnt;

        public QueueWithPriority(int size, int location) {
            this.size = size;
            priorities = new int[size+1];
            enqueued = new BitSet(size+1);
            idxToFind = location;
        }

        public void enqueue(int priority) {
            fullCheck();

            priorities[rear] = priority;

            rear++;
            rear %= size;
        }

        public void dequeue() {
            emptyCheck();

            int curPriority = peek();

            for (int i = 0; i < size; i++) {
                if (enqueued.get(i)) continue;

                if (priorities[i] > curPriority) {
                    nextFront();
                    return;
                }
            }

            enqueued.set(front);
            if (front == idxToFind) isFind = true;

            cnt++;
            nextFront();
        }

        private void nextFront() {
            if (enqueued.cardinality() == size) return;

            do {
                front++;
                front %= size;
            } while (enqueued.get(front));
        }

        private int peek() {
            emptyCheck();

            return priorities[front];
        }

        private void emptyCheck() {
            if (enqueued.cardinality() == size) throw new IllegalStateException("empty");
        }

        private void fullCheck() {
            if ((rear+1)% priorities.length == front) throw new IllegalStateException("full");
        }

        public boolean isFind() {
            return isFind;
        }

        public int getDequeueCnt() {
            return cnt;
        }
    }
}
