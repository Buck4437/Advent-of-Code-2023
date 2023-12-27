package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Main {

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

        int loopSize = 0;
        int[] pos = startPos;
        while (true) {
            // Move
            pos = getNextPos(pos, direction);

            loopSize++;

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

        // Answer
        System.out.println(loopSize / 2);
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

    // L, J, 7, F, |, -
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

}
