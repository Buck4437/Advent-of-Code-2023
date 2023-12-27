package Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.stream.Collectors;

class MainOOP {

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
        Hashtable<Hand, Long> table = new Hashtable<>();
        ArrayList<Hand> cards = new ArrayList<>();

        for (String line : inputList) {
            Hand hand = HandBuilder.buildHand(line.split(" ")[0]);
            long bet = Long.parseLong(line.split(" ")[1]);
            table.put(hand, bet);
            cards.add(hand);
        }

        // Weakest = first
        for (int i = cards.size() - 1; i > 0; i--) {
            for (int j = 1; j <= i; j++) {
                Hand card1 = cards.get(j-1);
                Hand card2 = cards.get(j);

                if (card1.beats(card2)) {
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
}

class HandBuilder {
    public static Hand buildHand(String s) {
        ArrayList<Card> cards = new ArrayList<>();
        for (char c : s.toCharArray()) {
            cards.add(new Card(Card.charToRank(c)));
        }
        Hand[] hands = {
                new Five(cards),
                new Quad(cards),
                new FullHouse(cards),
                new Triple(cards),
                new TwoPairs(cards),
                new Pair(cards),
                new High(cards),
        };

        for (Hand hand : hands) {
            if (hand.isValid()) {
                return hand;
            }
        }
        return null;
    }
}

class Card implements Comparable<Card>{

    public static final String suitOrder = "23456789TJQKA";

    private final int rank;

    public Card(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int compareTo(Card c) {
        return this.rank - c.getRank();
    }

    public static int charToRank(char c) {
        for (int i = 0; i < Card.suitOrder.length(); i++) {
            if (Card.suitOrder.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (rank == -1) {
            return "-";
        }
        return Character.toString(suitOrder.charAt(rank));
    }
}

abstract class Hand {

    private final ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    protected ArrayList<Card> getSortedCards() {
        ArrayList<Card> sorted = new ArrayList<>(cards);
        Collections.sort(sorted);
        return sorted;
    }

    public Card getCardAt(int idx) {
        return cards.get(idx);
    }

    public abstract int getRank();

    public abstract boolean isValid();

    public boolean beats(Hand hand) {
        if (getRank() > hand.getRank()) {
            return true;
        }

        if (getRank() < hand.getRank()) {
            return false;
        }

        for (int i = 0; i < 5; i++) {
            Card me = getCardAt(i), that = hand.getCardAt(i);
            if (me.compareTo(that) > 0) {
                return true;
            }
            if (me.compareTo(that) < 0) {
                return false;
            }
        }
        return false;
    }

    public String toString() {
        return cards.stream().map(Card::toString).collect(Collectors.joining());
    }

}

class Five extends Hand {

    public Five(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        Five sortedHand = new Five(getSortedCards());
        return sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(4).getRank();
    }

    public int getRank() {
        return 7;
    }

}

class Quad extends Hand {

    public Quad(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        Quad sortedHand = new Quad(getSortedCards());
        return sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(3).getRank()
                || sortedHand.getCardAt(1).getRank() == sortedHand.getCardAt(4).getRank();
    }

    public int getRank() {
        return 6;
    }

}

class FullHouse extends Hand {

    public FullHouse(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        FullHouse sortedHand = new FullHouse(getSortedCards());

        return (sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(1).getRank()
                && sortedHand.getCardAt(2).getRank() == sortedHand.getCardAt(4).getRank())
                ||
                (sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(2).getRank()
                && sortedHand.getCardAt(3).getRank() == sortedHand.getCardAt(4).getRank());
    }

    public int getRank() {
        return 5;
    }

}

class Triple extends Hand {

    public Triple(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        Triple sortedHand = new Triple(getSortedCards());

        return sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(2).getRank()
                || sortedHand.getCardAt(1).getRank() == sortedHand.getCardAt(3).getRank()
                || sortedHand.getCardAt(2).getRank() == sortedHand.getCardAt(4).getRank();
    }

    public int getRank() {
        return 4;
    }

}

class TwoPairs extends Hand {

    public TwoPairs(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        TwoPairs sortedHand = new TwoPairs(getSortedCards());

        return (sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(1).getRank()
                && (sortedHand.getCardAt(2).getRank() == sortedHand.getCardAt(3).getRank()
                        || sortedHand.getCardAt(3).getRank() == sortedHand.getCardAt(4).getRank())
        ) || (sortedHand.getCardAt(1).getRank() == sortedHand.getCardAt(2).getRank()
                && sortedHand.getCardAt(3).getRank() == sortedHand.getCardAt(4).getRank());
    }

    public int getRank() {
        return 3;
    }

}

class Pair extends Hand {

    public Pair(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        Pair sortedHand = new Pair(getSortedCards());

        return sortedHand.getCardAt(0).getRank() == sortedHand.getCardAt(1).getRank()
                || sortedHand.getCardAt(1).getRank() == sortedHand.getCardAt(2).getRank()
                || sortedHand.getCardAt(2).getRank() == sortedHand.getCardAt(3).getRank()
                || sortedHand.getCardAt(3).getRank() == sortedHand.getCardAt(4).getRank();

    }

    public int getRank() {
        return 2;
    }

}

class High extends Hand {

    public High(ArrayList<Card> cards) {
        super(cards);
    }

    public boolean isValid() {
        return true;
    }

    public int getRank() {
        return 1;
    }

}