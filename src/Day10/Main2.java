package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Main2 {

    static ArrayList<String> inputList = new ArrayList<>();

    static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day10/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        part1();
    }

    public static void part1() {
        int[] startPos = getStartPos();
        int direction = getStartDirection(startPos);

        int[] pos = startPos;
        ArrayList<int[]> visited = new ArrayList<>();

        while (true) {
            // Move
            pos = getNextPos(pos, direction);

            visited.add(pos);

            int r = pos[0], c = pos[1];

            char curTile = getCharacter(r, c);
            if (curTile == 'S') {
                break;
            } else if (curTile == '.') {
                System.out.println("WTF");
            } else {
                direction = getNextMove(curTile, direction);
            }
        }

        ArrayList<String> loop = getProcessedLoop(visited);

        System.out.println(countInside(loop));
    }

    static int[][] neighOffsets = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static int countInside(ArrayList<String> loop) {
        int rCount = loop.size() * 3, cCount = loop.get(0).length() * 3;

        boolean[][] visited = new boolean[rCount][cCount];

        // Mark some tiles as true according to the processed loop
        markVisited(visited, loop);

        visualizeVisited(visited);

        HashSet<int[]> toVisit = new HashSet<>();
        toVisit.add(new int[]{0, 0});

        while (toVisit.size() > 0) {
            HashSet<int[]> next = new HashSet<>();
            for (int[] coord: toVisit) {
                int r = coord[0], c = coord[1];
                if (!visited[r][c]) {
                    visited[r][c] = true;

                    for (int[] offset : neighOffsets) {
                        int newR = r + offset[0], newC = c + offset[1];
                        if (0 <= newR && newR < rCount && 0 <= newC && newC < cCount) {
                            if (!visited[newR][newC]) {
                                next.add(new int[]{newR, newC});
                            }
                        }
                    }
                }
            }
            toVisit = next;
        }

        int count = 0;

        // Sample center square of every tile
        for (int r = 0; r < rCount / 3; r++) {
            for (int c = 0; c < cCount / 3; c++) {
                if (!visited[r * 3 + 1][c * 3 + 1]) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void visualizeVisited(boolean[][] visited) {
        for (boolean[] blob : visited) {
            for (boolean bool : blob) {
                System.out.print(bool ? "#" : ".");
            }
            System.out.println();
        }
    }

    static final String patternF = "000011010";
    static final String patternL = "010011000";
    static final String patternJ = "010110000";
    static final String pattern7 = "000110010";
    // -
    static final String patternH = "000111000";
    // |
    static final String patternV = "010010010";

    public static void markVisited(boolean[][] visited, ArrayList<String> loop) {
        int rCount = loop.size(), cCount = loop.get(0).length();

        for (int r = 0; r < rCount; r++) {
            for (int c = 0; c < cCount; c++) {
                // Mark a 3 x 3 area
                char chr = loop.get(r).charAt(c);
                if (chr == '.') {
                    continue;
                }
                
                for (int i = 0; i < 9; i++) {
                    int rx = i / 3, cx = i % 3;
                    switch (chr) {
                        case 'F' -> { visited[r * 3 + rx][c * 3 + cx] = patternF.charAt(i) == '1'; break; }
                        case 'L' -> { visited[r * 3 + rx][c * 3 + cx] = patternL.charAt(i) == '1'; break; }
                        case '7' -> { visited[r * 3 + rx][c * 3 + cx] = pattern7.charAt(i) == '1'; break; }
                        case 'J' -> { visited[r * 3 + rx][c * 3 + cx] = patternJ.charAt(i) == '1'; break; }
                        case '|' -> { visited[r * 3 + rx][c * 3 + cx] = patternV.charAt(i) == '1'; break; }
                        case '-' -> { visited[r * 3 + rx][c * 3 + cx] = patternH.charAt(i) == '1'; break; }
                        default -> { System.out.println("AAAAAA"); }
                    }
                }
            }
        }
    }

    public static ArrayList<String> getProcessedLoop(ArrayList<int[]> visited) {
        HashMap<String, Character> mapping = new HashMap<>();
        int rCount = inputList.size(), cCount = inputList.get(0).length();

        for (int r = 0; r < rCount; r++) {
            for (int c = 0; c < cCount; c++) {
                mapping.put(r + "," + c, '.');
            }
        }

        for (int[] coord : visited) {
            int r = coord[0], c = coord[1];
            char chr = getCharacter(r, c);
            if (chr == 'S') {
                mapping.put(coord[0] + "," + coord[1], getStartChar(coord));
            } else {
                mapping.put(coord[0] + "," + coord[1], chr);
            }
        }

        ArrayList<String> loop = new ArrayList<>();
        for (int r = 0; r < rCount; r++) {
            StringBuilder str = new StringBuilder();
            for (int c = 0; c < cCount; c++) {
                str.append(mapping.get(r + "," + c));
            }
            loop.add(str.toString());
        }
        return loop;
    }

    public static char getCharacter(int r, int c) {

        int colCount = inputList.get(0).length();
        if (r < 0 || r > inputList.size() - 1 || c < 0 || c > colCount - 1) {
            return '.';
        }
        return inputList.get(r).charAt(c);
    }

    public static int[] getStartPos() {
        for (int r = 0; r < inputList.size(); r++) {
            for (int c = 0; c < inputList.get(r).length(); c++) {
                if (inputList.get(r).charAt(c) == 'S') {
                    return new int[]{r, c};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static int getNextMove(char curTile, int move) {
        switch (curTile) {
            case 'L' -> {
                return move == DOWN ? RIGHT : UP;
            }
            case 'J' -> {
                return move == DOWN ? LEFT : UP;
            }
            case '7' -> {
                return move == UP ? LEFT : DOWN;
            }
            case 'F' -> {
                return move == UP ? RIGHT : DOWN;
            }
            case '|', '-' -> {
                return move;
            }
        }
        System.out.println("You fucked up");
        return -1;
    }

    public static int[] getNextPos(int[] pos, int move) {
        int r = pos[0], c = pos[1];

        switch (move) {
            case UP -> { return new int[]{r - 1, c}; }
            case DOWN -> { return new int[]{r + 1, c}; }
            case LEFT -> { return new int[]{r, c - 1}; }
            case RIGHT -> { return new int[]{r, c + 1}; }
        }
        return new int[]{ -1, -1 };
    }


    public static int getStartDirection(int[] startPos) {
        int r = startPos[0], c = startPos[1];

        // Upward
        switch (getCharacter(r - 1, c)) {
            case '7', 'F', '|' -> {
                return UP;
            }
        }

        // Downward

        switch (getCharacter(r + 1, c)) {
            case 'J', 'L', '|' -> {
                return DOWN;
            }
        }

        return LEFT;
    }

    public static char getStartChar(int[] startPos) {
        String bits = "";

        int r = startPos[0], c = startPos[1];

        // Upward
        switch (getCharacter(r - 1, c)) {
            case '7', 'F', '|' -> { bits += "1"; }
            default -> { bits += "0"; }
        }

        // Downward
        switch (getCharacter(r + 1, c)) {
            case 'J', 'L', '|' -> { bits += "1"; }
            default -> { bits += "0"; }
        }

        // Left
        switch (getCharacter(r, c - 1)) {
            case 'L', 'F', '-' -> { bits += "1"; }
            default -> { bits += "0"; }
        }

        // Right
        switch (getCharacter(r, c + 1)) {
            case 'J', '7', '-' -> { bits += "1"; }
            default -> { bits += "0"; }
        }

        // UDLR
        switch (bits) {
            case "1100": return '|';
            case "1010": return 'J';
            case "1001": return 'L';
            case "0110": return '7';
            case "0101": return 'F';
            case "0011": return '-';
        }

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return '.';
    }

}
