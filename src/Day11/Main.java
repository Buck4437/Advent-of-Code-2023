package Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day11/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        solve();
    }

    public static void solve() {

        int rCount = inputList.size(), cCount = inputList.get(0).length();

        ArrayList<int[]> galaxies = new ArrayList<>();
        ArrayList<Integer> emptyR = new ArrayList<>();
        ArrayList<Integer> emptyC = new ArrayList<>();

        for (int r = 0; r < rCount; r++) {
            emptyR.add(r);
        }

        for (int c = 0; c < cCount; c++) {
            emptyC.add(c);
        }

        for (int r = 0; r < rCount; r++) {
            for (int c = 0; c < cCount; c++) {
                char chr = inputList.get(r).charAt(c);
                if (chr == '#') {
                    galaxies.add(new int[]{r, c});
                    if (emptyR.contains(r)) {
                        emptyR.remove(Integer.valueOf(r));
                    }
                    if (emptyC.contains(c)) {
                        emptyC.remove(Integer.valueOf(c));
                    }
                }
            }
        }

        long total1 = 0;
        long total2 = 0;

        for (int i = 0; i < galaxies.size() - 1; i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                int r1 = galaxies.get(i)[0], c1 = galaxies.get(i)[1];
                int r2 = galaxies.get(j)[0], c2 = galaxies.get(j)[1];

                long dst1 = calcSteps(r1, r2, emptyR, 2) + calcSteps(c1, c2, emptyC, 2);
                long dst2 = calcSteps(r1, r2, emptyR, 1000000) + calcSteps(c1, c2, emptyC, 1000000);

                total1 += dst1;
                total2 += dst2;
            }
        }

        System.out.println(total1);
        System.out.println(total2);
    }

    public static long calcSteps(int start, int end, ArrayList<Integer> empty, int spacing) {
        if (start > end) {
            return calcSteps(end, start, empty, spacing);
        }

        long steps = end - start;
        for (int num : empty) {
            if (start < num && num < end) {
                steps += (spacing - 1);
            }
        }
        return steps;
    }

}
