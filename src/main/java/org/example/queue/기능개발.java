package org.example.queue;

import java.util.*;

public class 기능개발 {
    public static void main(String[] args) {
//        System.out.println(Arrays.toString(solution(new int[]{93, 30, 55}, new int[]{1, 30, 5}))); // [2, 1]
        System.out.println(Arrays.toString(solution(new int[]{95, 90, 99, 99, 80, 99}, new int[]{1, 1, 1, 1, 1, 1})));
    }

    // 기능
    // - 배포 가능: 진도 == 100%
    // - 뒤에 있는 기능: 앞에 있는 기능과 함께 배포되어야 함

    // progresses: 기능 순서 (의존성)
    // speeds: 작업별 개발 속도
    // 배포: 하루의 마지막 일과

    //  0   1   2
    // 93, 30, 55
    // 1   30  5

    // 의존성: 0 - 1 - 2

    // 1 day
    // 93+1, 30+30, 55+5

//    ...

    // 7 day
    // 93+(1*7), 30+(30*7), 55+(5*7)
    // complete: 0, 1

//    ...

    // 9 day
    // 93+(1*7), 30+(30*7), 55+(5*9)
    // complete: 2

    // => 2, 1

    public static int[] solution(int[] progresses, int[] speeds) {
        List<Integer> ans = new ArrayList<>();

        int acc = 1;
        Task bottomTask = new Task(progresses[0], speeds[0]);

        for (int i = 1; i < progresses.length; i++) {
            Task curTask = new Task(progresses[i], speeds[i]);

            if (bottomTask.daysUntilCompletion() >= curTask.daysUntilCompletion()) {
                acc++;
            } else {
                ans.add(acc);
                acc = 1;
                bottomTask = curTask;
            }
        }

        ans.add(acc);

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    static class Task {
        final int dailyWorkload;
        int curProgress;

        public Task(int curProgress, int dailyWorkload) {
            this.curProgress = curProgress;
            this.dailyWorkload = dailyWorkload;
        }

        public int daysUntilCompletion() {
            return (int) Math.ceil((100 - curProgress) / dailyWorkload);
        }
    }
}
