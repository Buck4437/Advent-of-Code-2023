package Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day01/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part1();
        part2();
    }

    public static void part1() {
        int sum = 0;
        for (String s : inputList) {
            sum += getLineSum(s);
        }
        System.out.println("Part 1: " + sum);
    }

    public static void part2() {
        int sum = 0;
        for (String s : inputList) {
            sum += getLineSum2(s);
        }
        System.out.println("Part 2: " + sum);
    }

    public static int getLineSum(String s) {
        String filtered = Arrays.stream(s.split(""))
                .filter(str -> str.matches("^\\d$"))
                .collect(Collectors.joining());
        String number = Character.toString(filtered.charAt(0))
                + filtered.charAt(filtered.length() - 1);
        return Integer.parseInt(number);
    }

    public static int getLineSum2(String s) {
        String filtered = Arrays.stream(replaceDigit(s).split(""))
                .filter(str -> str.matches("^\\d$"))
                .collect(Collectors.joining());
        String number = Character.toString(filtered.charAt(0))
                + filtered.charAt(filtered.length() - 1);
        return Integer.parseInt(number);
    }

    public static String replaceDigit(String s) {
        String[] digits = "zero one two three four five six seven eight nine".split(" ");
        for (int i = 0; i < 10; i++) {
            String digit = digits[i];
            String template = String.format("%s%s%s", digit, i, digit);
            s = s.replaceAll(digit, template);
        }
        return s;
    }

}