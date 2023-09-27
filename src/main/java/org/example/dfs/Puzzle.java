package org.example.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Puzzle {
    class Shape {
            int[][] points;
            int longSize;

            Shape(int[][] points) {
                this.points = points;
                longSize = findLongSize();
            }

            public void rotate90() {
                for (int i = 0; i < longSize; i++) {
                    for (int j = 0; j < longSize; j++) {
                        points[j][longSize-i-1] = points[i][j];
                    }
                }
            }

            private int findLongSize() {
                int max = 0, s1 = 0, s2 = 0;
                for (int i = 0; i < longSize; i++) {
                    for (int j = 0; j < longSize; j++) {
                        if (points[i][j] == 1) s1++;
                        if (points[j][i] == 1) s2++;
                    }
                    max = Math.max(max, Math.max(s1, s2));
                    s1 = s2 = 0;
                }

                return max;
            }
}

    int m, n;
    int[][] game_board2, table2;
    public int solution(int[][] game_board, int[][] table) {
        int answer = -1, m = table.length, n = table[0].length;
        game_board2 = game_board;
        table2 = table;
        List<Shape> boardShape = new ArrayList<>();
        List<Shape> tableShape = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (game_board2[i][j] == 0) {
                    int[][] points1 = new int[51][51];
                    dfs(i, j, game_board2, points1);
                    boardShape.add(new Shape(points1));
                }

                if (table2[i][j] == 1) {
                    int[][] points2 = new int[51][51];
                    dfs2(i, j, table2, points2);
                    tableShape.add(new Shape(points2));
                }
            }
        }

        // boardShape의 permutation으로 tableShape 매칭하고
        // 매치되면 블록 갯수 누적합시키고, 매치되지 않으면 회전해보기
        // 각 permutation 마다 누적합들의 가장 큰 값을 답으로 리턴한다

        return answer;
    }

    private void dfs(int y, int x, int[][] matrix, int[][] points) {
        if (y < 0 || y >= m || x < 0 || x <= n) return;
        if (matrix[y][x] == 1) return;

        matrix[y][x] = 1;
        points[y][x] = 1;

        dfs(y+1, x, matrix, points);
        dfs(y-1, x, matrix, points);
        dfs(y, x+1, matrix, points);
        dfs(y, x-1, matrix, points);
    }

    private void dfs2(int y, int x, int[][] matrix, int[][] points) {
        if (y < 0 || y >= m || x < 0 || x <= n) return;
        if (matrix[y][x] == 0) return;

        matrix[y][x] = 0;
        points[y][x] = 0;

        dfs(y+1, x, matrix, points);
        dfs(y-1, x, matrix, points);
        dfs(y, x+1, matrix, points);
        dfs(y, x-1, matrix, points);
    }

    // 출처 : chatgpt, https://youngyin.tistory.com/148

    public static int solution2(int[][] game_board, int[][] table) {
        List<int[][]> puzzles = seek(game_board, 0);
        List<int[][]> blanks = seek(table, 1);
        int answer = 0;

        for (int[][] puzzle : puzzles) {
            int idx = -1;
            for (int j = 0; j < blanks.size(); j++) {
                int temp = match(puzzle, blanks.get(j));
                if (temp > 0) {
                    answer += temp;
                    idx = j;
                    break;
                }
            }
            if (idx != -1) blanks.remove(idx);
        }

        return answer;
    }

    public static List<int[][]> seek(int[][] table, int puzzle) {
        int size = table.length;
        List<int[][]> itemList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] == puzzle) {
                    int[][] item = new int[size][size];
                    int[] shape = {i, i, j, j}; // max_r, min_r, max_c, min_c
                    Stack<int[]> stack = new Stack<>();
                    stack.push(new int[]{i, j});

                    while (!stack.isEmpty()) {
                        int[] pos = stack.pop();
                        int r = pos[0];
                        int c = pos[1];
                        shape = new int[]{
                                Math.max(shape[0], r),
                                Math.min(shape[1], r),
                                Math.max(shape[2], c),
                                Math.min(shape[3], c)
                        };
                        item[r][c] = 1;
                        table[r][c] = puzzle + 1;

                        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
                        for (int[] dir : directions) {
                            int nr = r + dir[0];
                            int nc = c + dir[1];
                            if (nr >= 0 && nr < size && nc >= 0 && nc < size && table[nr][nc] == puzzle) {
                                stack.push(new int[]{nr, nc});
                            }
                        }
                    }

                    int[][] resizedItem = new int[shape[0] - shape[1] + 1][shape[2] - shape[3] + 1];
                    for (int r = shape[1], y = 0; r <= shape[0]; r++, y++) {
                        for (int c = shape[3], x = 0; c <= shape[2]; c++, x++) {
                            resizedItem[y][x] = item[r][c];
                        }
                    }

                    itemList.add(resizedItem);
                }
            }
        }

        return itemList;
    }

    public static int match(int[][] puzzle, int[][] blank) {
        int[] puzzleShape = {puzzle.length, puzzle[0].length};
        int[] blankShape = {blank.length, blank[0].length};
        Arrays.sort(puzzleShape);
        Arrays.sort(blankShape);

        if (!Arrays.equals(puzzleShape, blankShape)) return 0;

        for (int i = 0; i < 4; i++) {
            if (isSame(puzzle, blank)) {
                return Arrays.stream(puzzle).mapToInt(row -> Arrays.stream(row).sum()).sum();
            }
            puzzle = rotate90(puzzle);
        }
        return 0;
    }

    public static boolean isSame(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) return false;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    public static int[][] rotate90(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = matrix[r][c];
            }
        }
        return rotated;
    }

    public static void main(String[] args) {
        int[][] game_board = new int[][] {{1,1,0,0,1,0},{0,0,1,0,1,0},{0,1,1,0,0,1},{1,1,0,1,1,1},{1,0,0,0,1,0},{0,1,1,1,0,0}};
        int[][] table = new int[][] {{1,0,0,1,1,0}, {1,0,1,0,1,0}, {0,1,1,0,1,1}, {0,0,1,0,0,0}, {1,1,0,1,1,0}, {0,1,0,0,0,0}};
        System.out.println(solution2(game_board, table));
    }
}
