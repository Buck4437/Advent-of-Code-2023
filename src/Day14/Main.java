package Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Main {

    static final char ROCK = 'O';
    static final char WALL = '#';
    static final char EMPTY = '.';

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day14/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        solve();
    }

    public static void solve() {
        int rCount = inputList.size(), cCount = inputList.get(0).length();
        char[][] grid = new char[rCount][cCount];
        ArrayList<int[]> rocks = new ArrayList<>();

        for (int r = 0; r < inputList.size(); r++) {
            String row = inputList.get(r);
            for (int c = 0; c < row.length(); c++) {
                char chr = row.charAt(c);

                if (chr == ROCK) {
                    rocks.add(new int[]{r, c});
                }
                grid[r][c] = chr;
            }
        }

        move(new int[]{-1, 0}, grid, rocks);

        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

        long weight = 0;
        for (int[] rock : rocks) {
            int rowIndex = rCount - rock[0];
            weight += rowIndex;
        }
        System.out.println(weight);
    }

    public static void move(int[] dir, char[][] grid, ArrayList<int[]> rocks) {
        while (true) {
            boolean moved = false;

            for (int[] rock : rocks) {
                int[] next = new int[]{rock[0] + dir[0], rock[1] + dir[1]};

                if (getCharAt(next[0], next[1], grid) == EMPTY) {
                    grid[next[0]][next[1]] = ROCK;
                    grid[rock[0]][rock[1]] = EMPTY;
                    rock[0] = next[0];
                    rock[1] = next[1];
                    moved = true;
                }
            }

            if (!moved) {
                break;
            }
        }
    }

    public static char getCharAt(int r, int c, char[][] grid) {
        int rCount = grid.length, cCount = grid[0].length;

        if (r < 0 || r >= rCount || c < 0 || c >= cCount) {
            return WALL;
        }
        return grid[r][c];
    }
}
