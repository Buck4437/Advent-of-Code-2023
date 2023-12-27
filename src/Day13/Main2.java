package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Main2 {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day13/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        solve();
    }

    public static void solve() {
        String inputString = String.join("\n", inputList);
        String[] fields = inputString.split("\n\n");
        long sum = 0;
        for (String field : fields) {
            sum += findLine(field);
        }
        System.out.println(sum);
    }

    public static int findLine(String field) {
        String[] grid = field.split("\n");
        int cCount = grid[0].length();

        // Row
        // 0(1)1(2)2(3)3
        for (int line = 1; line < grid.length; line++) {
            int smudge = 0;
            for (int r = 0; r < line; r++) {
                int reflected = r + (line - r) * 2 - 1;
                if (reflected < grid.length) {
                    for (int c = 0; c < cCount; c++) {
                        if (grid[r].charAt(c) != grid[reflected].charAt(c)) {
                            smudge++;
                        }
                    }
                }
            }
            if (smudge == 1) {
                return 100 * line;
            }
        }

        // Column
        for (int line = 1; line < cCount; line++) {
            int smudge = 0;
            for (int c = 0; c < line; c++) {
                int reflected = c + (line - c) * 2 - 1;
                if (reflected < cCount) {
                    for (String s : grid) {
                        if (s.charAt(c) != s.charAt(reflected)) {
                            smudge++;
                        }
                    }
                }
            }
            if (smudge == 1) {
                return line;
            }
        }

        System.out.println("Fuck");
        return 0;
    }
}
