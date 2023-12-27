package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day12/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        solve();
    }

    public static void solve() {
        int sum = 0;
        for (String s : inputList) {
            sum += countCases(s.split(" ")[0], s.split(" ")[1].split(","));
        }
        System.out.println(sum);
    }

    public static int countCases(String clue, String[] pattern) {
//        System.out.println(clue);
        if (!isValid(clue, pattern)) {
            return 0;
        }
        if (!clue.contains("?")) {
            return 1;
        }
        StringBuilder a = new StringBuilder(), b = new StringBuilder();
        boolean foundQuestionMark = false;
        for (char c : clue.toCharArray()) {
            if (c == '?' && !foundQuestionMark) {
                a.append("#");
                b.append(".");
                foundQuestionMark = true;
            } else {
                a.append(c);
                b.append(c);
            }
        }
        return countCases(a.toString(), pattern) + countCases(b.toString(), pattern);
    }

    public static boolean isValid(String str, String[] pattern) {
        String[] reversed = new String[pattern.length];
        for (int i = 0; i < pattern.length; i++) {
            reversed[pattern.length - 1 - i] = pattern[i];
        }
        return checkValid(str, pattern) &&
                checkValid(new StringBuilder(str).reverse().toString(), reversed);
    }

    public static boolean checkValid(String str, String[] pattern) {
        // Check invalid
        int blockCount = 0, blockIndex = 0;
        for (char c : (str + ".").toCharArray()) {
            if (c == '?') {
                return true;
            } else if (c == '#') {
                blockCount++;
            } else if (blockCount >= 1) {
                if (blockIndex >= pattern.length) {
                    return false;
                }
                if (blockCount != Integer.parseInt(pattern[blockIndex])) {
                    return false;
                }
                blockCount = 0;
                blockIndex++;
            }
        }
        return blockIndex == pattern.length;
//        System.out.println(str + Arrays.toString(pattern));
    }
}
