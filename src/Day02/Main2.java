package Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main2 {

    static ArrayList<String> inputList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day02/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        part2();
    }

    public static void part2() {
        int sum = 0;
        for (String line : inputList) {
            String[] games = getGames(line);

            sum += getMinCubePower(games);
        }
        System.out.println(sum);
    }


    public static String[] getGames(String line) {
        return line.split(": ")[1].split("; ");
    }

    public static int getMinCubePower(String[] games) {
        int[] minimum = new int[]{0, 0, 0};
        for (String game : games) {
            int[] balls = getBalls(game);
            for (int i = 0; i < 3; i++) {
                minimum[i] = Math.max(balls[i], minimum[i]);
            }
        }
        return minimum[0] * minimum[1] * minimum[2];
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