package Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day08/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part1();
    }

    public static void part1() {
        String move = inputList.get(0);

        Hashtable<String, Node> nodeList = new Hashtable<>();

        for (int i = 2; i < inputList.size(); i++) {
            String name = inputList.get(i).split(" = ")[0];
            nodeList.put(name, new Node(name));
        }

        for (int i = 2; i < inputList.size(); i++) {
            String name = inputList.get(i).split(" = ")[0];
            String[] pair = inputList.get(i).split(" = ")[1]
                    .replaceAll("[\\(\\)]", "").split(", ");

            nodeList.get(name).left = nodeList.get(pair[0]);
            nodeList.get(name).right = nodeList.get(pair[1]);
        }

        int step = 0;
        Node cur = nodeList.get("AAA");
        while (!cur.name.equals("ZZZ")) {
            char curStep = move.charAt(step % move.length());

            if (curStep == 'L') {
                cur = cur.left;
            } else {
                cur = cur.right;
            }

            step++;
        }

        System.out.println(step);
    }

}

class Node {

    public String name;

    public Node(String name) {
        this.name = name;
    }

    public Node left;
    public Node right;

}

