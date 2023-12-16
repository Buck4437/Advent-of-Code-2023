package Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Main_BinarySearch {

    static ArrayList<String[]> inputList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day06/input2.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arr = line.strip().split("\\s+");
            inputList.add(Arrays.copyOfRange(arr, 1, arr.length));
        }

        part2();
    }

    public static void part2() {
        long time = 46857582;
        long dst = 208141212571410L;

        long mul = 1;
        for (int a = 0; a < inputList.get(0).length; a++) {
            long low = 0, high = time;
            for (int i = (int) (time / 2 + 1); i >= 1; i /= 2) {
                if (calcDisc(time, low + i) <= dst) {
                    low += i;
                }
                if (calcDisc(time, high - i) <= dst) {
                    high -= i;
                }
            }
            mul *= high - low - 1;
        }
        System.out.println(mul);
    }


    public static long calcDisc(long time, long hold) {
        return (time - hold) * hold;
    }
}

