package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

class Main2 {

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
        long sum = 0;
        int count = 0;
        for (String s : inputList) {
            String clue = s.split(" ")[0];
            clue = clue + "?" + clue + "?" + clue + "?" + clue + "?" + clue;

            String pattern = s.split(" ")[1];
            pattern = pattern + "," + pattern + "," + pattern + "," + pattern + "," + pattern;
            sum += countCases(clue, pattern.split(","));
            System.out.println(++count);
        }
        System.out.println(sum);
    }

    public static Hashtable<String, Long> cache = new Hashtable<>();

    public static long countCases(String clue, String[] pattern) {
        String key = clue + "  " + String.join(",", pattern);

        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (!isValid(clue, pattern)) {
            cache.put(key, 0L);
            return 0;
        }
        if (!clue.contains("?")) {
            cache.put(key, 1L);
            return 1;
        }
        String a = "", b = "";
        boolean foundQuestionMark = false;
        String[] p2 = pattern;

//        System.out.println(clue + Arrays.toString(pattern));
        for (char c : clue.toCharArray()) {
            if (c == '?' && !foundQuestionMark) {
                a += "#";
                b += ".";
                foundQuestionMark = true;
            } else if (!foundQuestionMark && c == '.') {
                if (!a.equals("")) {
                    a = "";
                    b = "";
                    p2 = Arrays.copyOfRange(p2, 1, p2.length);
                }
            } else {
                a += c;
                b += c;
            }
        }
        long cases = countCases(a, p2) + countCases(b, p2);

        cache.put(key, cases);
        return cases;
    }

    public static boolean isValid(String str, String[] pattern) {
        // Check invalid
        int blockCount = 0, blockIndex = 0;
        for (char c : (str + ".").toCharArray()) {
            if (c == '?') {
                if (blockCount > 0 && blockCount > Integer.parseInt(pattern[blockIndex])) {
                    return false;
                }
                return true;
            } else if (c == '#') {
                if (blockIndex >= pattern.length) {
                    return false;
                }
                blockCount++;
            } else if (blockCount >= 1) {
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
