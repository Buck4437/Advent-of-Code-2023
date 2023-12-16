package Day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();
    static String inputString;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day05/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        inputString = String.join("\n", inputList);

        part1();
    }

    public static void part1() {
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
        long lowest = 999999999;
        for (String seed : seeds.split(" ")) {
            long intSeed = Long.parseLong(seed);
            for (String mappings : parsedCategories) {
//                System.out.println(intSeed);
                intSeed = transform(intSeed, mappings.split("\n"));
            }
            lowest = Math.min(lowest, intSeed);
//            System.out.println("Finally: " + intSeed);
        }
        System.out.println(lowest);
    }

    public static long transform(long seed, String[] mappings) {
        for (String mapping : mappings) {
            // Bruh
            long end = Long.parseLong(mapping.split(" ")[0]);
            long start = Long.parseLong(mapping.split(" ")[1]);
            long range = Long.parseLong(mapping.split(" ")[2]);

            if (seed >= start && seed <= start + range - 1) {
                return seed + end - start;
            }
        }
        return seed;
    }
}

