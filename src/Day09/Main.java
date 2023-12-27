package Day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day09/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part1();
    }

    public static void part1() {
        long sum = 0;
        for (String s : inputList) {
            sum += solve(s.split(" "));
        }
        System.out.println(sum);
    }

    public static long solve(String[] raw) {
        ArrayList<Long> longs = new ArrayList<>();
        for (String s : raw) {
            longs.add(Long.parseLong(s));
        }

        ArrayList<Long> last = new ArrayList<>();

        while (true) {
            last.add(longs.get(longs.size() - 1));
            boolean flag = true;

            ArrayList<Long> list2 = new ArrayList<>();
            for (int i = 0; i < longs.size(); i++) {
                if (longs.get(i) != 0) {
                    flag = false;
                }

                if (i < longs.size() - 1) {
                    list2.add(longs.get(i + 1) - longs.get(i));
                }
            }
            if (flag) {
                break;
            }

            longs = list2;
        }

        long sum = 0;
        for (var l : last) {
            sum += l;
        }
        return sum;
    }

}
