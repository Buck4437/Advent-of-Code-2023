package Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

class Main2 {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day08/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part2();
    }

    public static void part2() {
        String move = inputList.get(0);

        Hashtable<String, Node2> nodeList = new Hashtable<>();
        ArrayList<String> starts = new ArrayList<>();

        for (int i = 2; i < inputList.size(); i++) {
            String name = inputList.get(i).split(" = ")[0];
            if (name.charAt(2) == 'A') {
                starts.add(name);
            }
            nodeList.put(name, new Node2(name));
        }

        for (int i = 2; i < inputList.size(); i++) {
            String name = inputList.get(i).split(" = ")[0];
            String[] pair = inputList.get(i).split(" = ")[1]
                    .replaceAll("[\\(\\)]", "").split(", ");

            nodeList.get(name).left = nodeList.get(pair[0]);
            nodeList.get(name).right = nodeList.get(pair[1]);
        }

        ArrayList<Integer> offsets = new ArrayList<>();
        ArrayList<Integer> cycleLengths = new ArrayList<>();

        // Observation: step = offset
        // Idea: Find all cycle lengths, then find LCM
        for (String start : starts) {
            int step = 0;
            int offset = 0;
            Node2 cur = nodeList.get(start);
            String firstOcc = null;
            while (true) {
                char curStep = move.charAt(step % move.length());

                if (curStep == 'L') {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }

                step++;

                if (cur.name.charAt(2) == 'Z') {
                    if (firstOcc == null) {
                        offset = step;
                        firstOcc = cur.name;
                    } else if (Objects.equals(firstOcc, cur.name)) {
                        break;
                    }
                }
            }
            offsets.add(offset);
            cycleLengths.add(step - offset);
        }

        long lcm = 1;
        for (long offset : offsets) {
            lcm = LCM(offset, lcm);
        }
        System.out.println(lcm);
//
//        System.out.println("blobsad");
    }

    public static long LCM(long a, long b) {
        long gcd = GCD(a, b);
        return a * b / gcd;
    }

    public static long GCD(long a, long b) {
        while (a != 1 && b != 1) {
            if (a > b) {
                if ((a / b) * b == a) {
                    return b;
                }
                a = a - (a / b) * b;
            } else {
                if ((b / a) * a == b) {
                    return a;
                }
                b = b - (b / a) * a;
            }
        }
        return 1;
    }

}


class Node2 {

    public String name;

    public Node2(String name) {
        this.name = name;
    }

    public Node2 left;
    public Node2 right;

}

