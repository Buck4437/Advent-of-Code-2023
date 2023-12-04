package Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day03/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part1();
    }

    public static void part1() {
        HashSet<String> symbolNeighbourCoords = new HashSet<>();
        for (int r = 0; r < inputList.size(); r++) {
            String row = inputList.get(r);
            for (int c = 0; c < row.length(); c++) {
                char chr = row.charAt(c);
                if ((chr < '0' || chr > '9') && chr != '.') {
                    int[][] neighbours = getNeighbours(r, c);
                    for (int[] neighbour : neighbours) {
                        symbolNeighbourCoords.add(hashCoord(neighbour));
                    }
                }
            }
        }

        int numSum = 0;

        for (int r = 0; r < inputList.size(); r++) {
            String row = inputList.get(r);
            String digit = "";
            boolean isAdjacent = false;
            for (int c = 0; c < row.length(); c++) {
                char chr = row.charAt(c);
                if ('0' <= chr && chr <= '9') {
                    digit = digit + chr;
                    String locationHash = hashCoord(new int[]{r, c});
                    if (symbolNeighbourCoords.contains(locationHash)) {
                        isAdjacent = true;
                    }
                } else {
                    if (isAdjacent) {
                        numSum += Integer.parseInt(digit);
                    }
                    digit = "";
                    isAdjacent = false;
                }
            }
            if (isAdjacent) {
                numSum += Integer.parseInt(digit);
            }
        }

        System.out.println(numSum);
    }

    public static String hashCoord(int[] coord) {
        return Integer.toString(coord[0]) + "," + coord[1];
    }

    public static int[][] getNeighbours(int r, int c) {
        int[][] neighbours = new int[8][];
        int idx = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    neighbours[idx] = new int[]{r + i, c + j};
                    idx++;
                }
            }
        }
        return neighbours;
    }

}

