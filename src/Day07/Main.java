package Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();
    static String inputString;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day07/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        inputString = String.join("\n", inputList);

        part1();
    }

    public static void part1() {
        Hashtable<String, Long> table = new Hashtable<>();
        ArrayList<String> cards = new ArrayList<>();

        for (String line : inputList) {
            String hand = line.split(" ")[0];
            long bet = Long.parseLong(line.split(" ")[1]);
            table.put(hand, bet);
            cards.add(hand);
        }

        // Weakest = first
        for (int i = cards.size() - 1; i > 0; i--) {
            for (int j = 1; j <= i; j++) {
                String card1 = cards.get(j-1);
                String card2 = cards.get(j);

                if (compare(card1, card2) > 0) {
                    cards.set(j-1, card2);
                    cards.set(j, card1);
                }
            }
        }

        long total = 0;
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(cards.get(i));
            total += (i + 1) * table.get(cards.get(i));
        }
        System.out.println(total);
    }

    public static int compare(String hand1, String hand2) {
        int rankA = handToRank(hand1), rankB = handToRank(hand2);
        if (rankA > rankB) {
            return 1;
        }
        if (rankA < rankB) {
            return -1;
        }
        for (int i = 0; i < 5; i++) {
            int idxA = cardToIdx(hand1.charAt(i)), idxB = cardToIdx(hand2.charAt(i));
            if (idxA > idxB) {
                return 1;
            }
            if (idxA < idxB) {
                return -1;
            }
        }
        return 0;
    }

    public static int handToRank(String hand) {
        char[] sorted = hand.toCharArray();
        Arrays.sort(sorted);

        if (sorted[0] == sorted[4]) {
            return 7;
        } else if (sorted[0] == sorted[3] || sorted[1] == sorted[4]) {
            return 6;
        } else if ((sorted[0] == sorted[1] && sorted[2] == sorted[4])
                || (sorted[0] == sorted[2] && sorted[3] == sorted[4])) {
            return 5;
        } else if (sorted[0] == sorted[2] || sorted[1] == sorted[3] || sorted[2] == sorted[4]) {
            return 4;
        } else if ((sorted[0] == sorted[1] && (sorted[2] == sorted[3] || sorted[3] == sorted[4]))
                || (sorted[1] == sorted[2] && sorted[3] == sorted[4])) {
            return 3;
        } else if (sorted[0] == sorted[1] || sorted[1] == sorted[2] || sorted[2] == sorted[3] || sorted[3] == sorted[4]) {
            return 2;
        }
        return 1;
    }

    public static int cardToIdx(char c) {
        String s = "23456789TJQKA";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }
}

