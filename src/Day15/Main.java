package Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Main {
    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day15/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        solve();
    }

    public static void solve() {
        String inputStr = String.join("", inputList);
        String[] hashes = inputStr.split(",");
        long total = 0;
        for (String hash : hashes) {
            total += hashify(hash);
        }
        System.out.println(total);
    }

    public static long hashify(String s) {
        int val = 0;
        for (char c : s.toCharArray()) {
            val += c;
            val *= 17;
            val %= 256;
        }
        return val;
    }
}
