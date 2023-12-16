package Day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main3 {

    static ArrayList<String> inputList = new ArrayList<>();
    static String inputString;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day05/input.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            inputList.add(line.strip());
        }

        inputString = String.join("\n", inputList);

        long t0 = System.nanoTime();
        part2();
        double runTime = (System.nanoTime() - t0) / 1e9;
        System.out.println("Runtime: " + runTime + "s");
    }

    public static void part2() {
        String seeds = inputString.split("\n\n")[0].split(": ")[1];
        ArrayList<String> categories = new ArrayList<>(Arrays.asList(inputString.split("\n\n")));
        categories.remove(0);
        String[] parsedCategories = categories.stream()
                .map(x -> x.split("\n"))
                .map(x -> String.join("\n", Arrays.copyOfRange(x, 1, x.length)))
                .toArray(String[]::new);

        solve(seeds, parsedCategories);
    }

    public static void solve(String seeds, String[] parsedCategories) {
        ArrayList<Range> ranges = toRanges(seeds.split(" "));
        for (String mappings : parsedCategories) {
            ranges = transformRange(ranges, mappings.split("\n"));
        }
        System.out.println(findMin(ranges));
    }

    public static ArrayList<Range> toRanges(String[] seeds) {
        ArrayList<Range> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.length; i += 2) {
            long start = Long.parseLong(seeds[i]), range = Long.parseLong(seeds[i + 1]);
            ranges.add(new Range(start, start + range - 1));
        }
        return ranges;
    }

    public static long findMin(ArrayList<Range> ranges) {
        long min = 9999999999999L;
        for (Range range : ranges) {
            min = Math.min(range.s, min);
        }
        return min;
    }

    public static ArrayList<Range> transformRange(ArrayList<Range> ranges, String[] mappings) {
        ArrayList<Range> rangesCopy = new ArrayList<>(ranges);

        ArrayList<Range> transformed = new ArrayList<>();

        for (String mapping : mappings) {
            ArrayList<Range> temp = new ArrayList<>();

            Transform transform = new Transform(mapping);
            Range toRemove = transform.getInputRange();

            // Range to be transformed
            for (Range range : rangesCopy) {
                // No overlap
                if (range.e < toRemove.s || toRemove.e < range.s) {
                    temp.add(range);
                }

                // Fully transformed
                // [        (remove)              ]
                //         [ (our range)   ]
                else if (toRemove.s <= range.s && range.e <= toRemove.e) {
                    transformed.add(transform.transformRange(range));
                }

                // Fully contained (without touching the side)
                // [       (our range)      ]
                //        [ (remove)   ]
                else if (range.s < toRemove.s && toRemove.e < range.e) {
                    // Slice into three parts;
                    temp.add(new Range(range.s, toRemove.s - 1));
                    temp.add(new Range(toRemove.e + 1, range.e));
                    transformed.add(transform.transformRange(new Range(toRemove.s, toRemove.e)));
                }

                // Partially contained
                // [   (our range)   ]
                //            [    (remove)   ]
                else if (range.e <= toRemove.e) {
                    temp.add(new Range(range.s, toRemove.s - 1));
                    transformed.add(transform.transformRange(new Range(toRemove.s, range.e)));
                }

                //             [   (our range)   ]
                //    [    (remove)   ]
                else {
                    temp.add(new Range(toRemove.e + 1, range.e));
                    transformed.add(transform.transformRange(new Range(range.s, toRemove.e)));
                }

            }
            rangesCopy = temp;
        }

        rangesCopy.addAll(transformed);
        return rangesCopy;
    }
}

class Range {
    public long s;
    public long e;

    public Range(long start, long end) {
        this.s = start;
        this.e = end;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", s, e);
    }
}

class Transform {
    public long end;
    public long start;
    public long range;

    public Transform(String mapping) {
        end = Long.parseLong(mapping.split(" ")[0]);
        start = Long.parseLong(mapping.split(" ")[1]);
        range = Long.parseLong(mapping.split(" ")[2]);
    }

    public Range getInputRange() {
        return new Range(start, start + range - 1);
    }

    public Range transformRange(Range r) {
        return new Range(r.s + end - start, r.e + end - start);
    }
}