package Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Main2 {
    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day15/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }
        solve();
    }

    static ArrayList<ArrayList<Lens>> hashMap = new ArrayList<>();

    public static void solve() {
        for (int i = 0; i < 256; i++) {
            hashMap.add(new ArrayList<>());
        }

        String inputStr = String.join("", inputList);
        String[] instructions = inputStr.split(",");

        for (String instruction : instructions) {
            String lensLabel = instruction.split("[-=]")[0];
            int hash = hashify(lensLabel);

            ArrayList<Lens> list = hashMap.get(hash);
            ArrayList<Lens> newList = new ArrayList<>();

            if (instruction.contains("-")) {
                for (Lens n : list) {
                    if (!n.name.equals(lensLabel)) {
                        newList.add(n);
                    }
                }
            } else {
                int power = Integer.parseInt(instruction.charAt(instruction.length() - 1) + "");
                boolean found = false;
                for (Lens n : list) {
                    if (!n.name.equals(lensLabel)) {
                        newList.add(n);
                    } else {
                        found = true;
                        newList.add(new Lens(lensLabel, power));
                    }
                }

                if (!found) {
                    newList.add(new Lens(lensLabel, power));
                }
            }
            hashMap.set(hash, newList);
        }

        long total = 0;

        for (int i = 0; i < 256; i++) {
            ArrayList<Lens> box = hashMap.get(i);
            for (int slot = 0; slot < box.size(); slot++) {
                Lens lens = box.get(slot);

                total += (long) (i + 1) * (slot + 1) * lens.power;
            }
        }
        System.out.println(total);
    }

    public static int hashify(String s) {
        int val = 0;
        for (char c : s.toCharArray()) {
            val += c;
            val *= 17;
            val %= 256;
        }
        return val;
    }
}


class Lens {

    String name;
    int power;

    public Lens(String name, int power) {
        this.name = name;
        this.power = power;
    }

}