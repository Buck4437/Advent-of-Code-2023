package Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Main2 {

    private static class NumObj {
        int num;
        int numId;
        ArrayList<String> coords;
    }

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day03/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part2();
    }

    public static void part2() {
        int sum = 0;

        HashMap<Integer, Integer> numIdToNum = new HashMap<>();
        HashMap<String, Integer> coordToNumId = new HashMap<>();

        for (int r = 0; r < inputList.size(); r++) {
            String row = inputList.get(r);
            String digits = "";
            ArrayList<String> coords = new ArrayList<>();
            for (int c = 0; c < row.length(); c++) {
                char chr = row.charAt(c);
                if ('0' <= chr && chr <= '9') {
                    digits = digits + chr;
                    coords.add(hashCoord(new int[]{r, c}));
                } else {
                    NumObj n = createNumObj(digits, coords);
                    if (n != null) {
                        for (String coord : n.coords) {
                            coordToNumId.put(coord, n.numId);
                        }
                        numIdToNum.put(n.numId, n.num);
                    }
                    coords = new ArrayList<>();
                    digits = "";
                }

                NumObj n = createNumObj(digits, coords);
                if (n != null) {
                    for (String coord : n.coords) {
                        coordToNumId.put(coord, n.numId);
                    }
                    numIdToNum.put(n.numId, n.num);
                }
            }
        }

        for (int r = 0; r < inputList.size(); r++) {
            String row = inputList.get(r);
            for (int c = 0; c < row.length(); c++) {
                char chr = row.charAt(c);
                if (chr == '*') {
                    int[][] neighbours = getNeighbours(r, c);
                    HashSet<Integer> numIds = new HashSet<>();

                    for (int[] neighbour : neighbours) {
                        String hashedCoord = hashCoord(neighbour);
                        if (coordToNumId.containsKey(hashedCoord)) {
                            numIds.add(coordToNumId.get(hashedCoord));
                        }
                    }

                    if (numIds.size() == 2) {
                        int gearRatio = 1;
                        for (int numId : numIds) {
                            gearRatio *= numIdToNum.get(numId);
                        }
                        sum += gearRatio;
                    }
                }
            }
        }

        System.out.println(sum);
    }

    static int _numId = 0;

    public static NumObj createNumObj(String digits, ArrayList<String> coords) {
        if (digits.equals("")) {
            return null;
        }
        NumObj obj = new Main2.NumObj();
        obj.num = Integer.parseInt(digits);
        obj.numId = _numId;
        obj.coords = coords;

        _numId++;

        return obj;
    }

    public static String hashCoord(int[] coord) {
        return coord[0] + "," + coord[1];
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

