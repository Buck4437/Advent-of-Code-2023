package Day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Main2 {

    static ArrayList<String> inputList = new ArrayList<>();
    static String inputString;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day05/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        inputString = String.join("\n", inputList);

        part2();
    }

    public static void part2() {
        String seeds = inputString.split("\n\n")[0].split(": ")[1];
        ArrayList<String> categories = new ArrayList<>(Arrays.asList(inputString.split("\n\n")));
        categories.remove(0);
        String[] parsedCategories = categories.stream()
                .map(x -> x.split("\n"))
                .map(x -> String.join("\n", Arrays.copyOfRange(x, 1, x.length)))
                .toArray(String[]::new);

        solve(seeds, parsedCategories);
    }

    public static void solve(String seeds, String[] parsedCategories) {
        long testSeed = 0;
        while (true) {
            long seed = testSeed;
            for (int i = parsedCategories.length - 1; i >= 0; i--) {
                String[] mappings = parsedCategories[i].split("\n");
                seed = reverseTransform(seed, mappings);
            }
            if (withinRange(seed, seeds.split(" "))) {
                System.out.println(testSeed);
                return;
            }
            testSeed++;
            if (testSeed % 1000 == 0) {
                System.out.println(testSeed);
            }
        }
    }

    public static boolean withinRange(long seed, String[] seeds) {
        for (int i = 0; i < seeds.length; i += 2) {
            long start = Long.parseLong(seeds[i]), range = Long.parseLong(seeds[i + 1]);
            if (start <= seed && seed <= start + range - 1) {
                return true;
            }
        }
        return false;
    }

    public static long reverseTransform(long seed, String[] mappings) {
        for (String mapping : mappings) {
            long end = Long.parseLong(mapping.split(" ")[0]);
            long start = Long.parseLong(mapping.split(" ")[1]);
            long range = Long.parseLong(mapping.split(" ")[2]);

            if (seed >= end && seed <= end + range - 1) {
                return seed - end + start;
            }
        }
        return seed;
    }
}

