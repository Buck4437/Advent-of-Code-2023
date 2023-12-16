package Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Main {

    static ArrayList<String[]> inputList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day06/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arr = line.strip().split("\\s+");
            inputList.add(Arrays.copyOfRange(arr, 1, arr.length));
        }

        part1();
    }

    public static void part1() {
        long mul = 1;
        for (int i = 0; i < inputList.get(0).length; i++) {
            long time = Long.parseLong(inputList.get(0)[i]);
            long dst = Long.parseLong(inputList.get(1)[i]);
            int validCount = 0;
            for (int hold = 0; hold < time; hold++) {
                if (calcDisc(time, hold) > dst) {
                    validCount++;
                }
            }
            mul *= validCount;
        }
        System.out.println(mul);
    }

    public static long calcDisc(long time, long hold) {
        return (time - hold) * hold;
    }
}

