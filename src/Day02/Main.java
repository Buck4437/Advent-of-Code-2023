package Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Main {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day02/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part1();
    }

    public static void part1() {
        int sum = 0;
        int[] maxBalls = new int[]{12, 13, 14};
        for (String line : inputList) {
            int id = getGameId(line);
            String[] games = getGames(line);

            if (areValidGames(games, maxBalls)) {
                sum += id;
            }
        }
        System.out.println(sum);
    }

    public static int getGameId(String s) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher matcher = p.matcher(s);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    public static String[] getGames(String line) {
        return line.split(": ")[1].split("; ");
    }

    public static boolean areValidGames(String[] games, int[] maxBalls) {
        for (String game : games) {
            if (!isValidGame(game, maxBalls)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidGame(String game, int[] maxBalls) {
        int[] balls = getBalls(game);
        for (int i = 0; i < 3; i++) {
            if (balls[i] > maxBalls[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] getBalls(String game) {
        Pattern[] patterns = new Pattern[]{
                Pattern.compile("(\\d+) red"),
                Pattern.compile("(\\d+) green"),
                Pattern.compile("(\\d+) blue")
        };

        int[] balls = new int[]{0, 0, 0};
        for (int i = 0; i < 3; i++) {
            Matcher m = patterns[i].matcher(game);
            if (m.find()) {
                balls[i] = Integer.parseInt(m.group(1));
            }
        }

        return balls;
    }

}