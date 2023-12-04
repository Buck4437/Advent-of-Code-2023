package Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day04/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part1();
    }

    public static void part1() {
        int sum = 0;
        for (String card : inputList) {
            int count = 0;
            String numSegment = card.split(": ")[1];
            HashSet<String> win = cardToSet(numSegment.split(" \\| ")[0].strip());
            HashSet<String> nums = cardToSet(numSegment.split(" \\| ")[1].strip());

            for (String num : nums) {
                if (win.contains(num)) {
                    count++;
                }
            }

            if (count > 0) {
                sum += Math.pow(2, count - 1);
            }
        }
        System.out.println(sum);
    }

    public static HashSet<String> cardToSet(String card) {
        return new HashSet<>(Arrays.asList(card.split("\\s+")));
    }
}

