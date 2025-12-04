package org.mikul17.DayThree;


import java.io.IOException;
import java.nio.file.Files;

import static org.mikul17.utils.PathUtils.getFilePath;

public class DayThree {

    private static Integer findLargest(String num){
        int highest = 0;

        int first = Integer.parseInt(String.valueOf(num.charAt(0)));
        int last = Integer.parseInt(String.valueOf(num.charAt(num.length() -1)));
        int optional = Integer.parseInt("%d%d".formatted(first, last));
        if(optional > highest){
            highest = optional;
        }

       for(int i =0; i<num.length(); i++){
           int currentFirst = Integer.parseInt(String.valueOf(num.charAt(i)));
           for(int j =i + 1; j<num.length(); j++){
               int currentSecond =  Integer.parseInt(String.valueOf(num.charAt(j)));
               int current = Integer.parseInt("%d%d".formatted(currentFirst, currentSecond));
               if(current > highest){
                   highest = current;
               }
           }

       }
        return highest;
    }

    private static Long findLargestTwo(String num) {
        int maxLength = 12;
        int remaining = num.length() - maxLength;

        StringBuilder builder = new StringBuilder();

        int start = 0;

        while (builder.length() < maxLength && start < num.length()) {
            int end = start + remaining;
            int max = -1;
            int maxIndex = start;

            if (end >= num.length()) {
                end = num.length() - 1;
            }

            for (int i = start; i <= end; i++) {
                int current = Integer.parseInt(String.valueOf(num.charAt(i)));
                if (current > max) {
                    max = current;
                    maxIndex = i;

                    if (current == 9) {
                        break;
                    }
                }
            }

            IO.println("Current window: %s".formatted(num.substring(start, end + 1)));
            IO.println("Selection %d [index: %d]".formatted(max, maxIndex));

            builder.append(max);

            int skipped = maxIndex - start;
            remaining -= skipped;
            start = maxIndex + 1;
        }

        while (builder.length() < maxLength && start < num.length()) {
            int fillingDigit =  Integer.parseInt(String.valueOf(num.charAt(start)));
            builder.append(fillingDigit);
            start++;
        }

        return Long.parseLong(builder.toString());
    }


    static void main() throws IOException {
        var input = Files.lines(getFilePath(DayThree.class, "input.txt")).toList();

        Integer ans_one = input.stream().map(DayThree::findLargest).reduce(0, Integer::sum);

        Long ans_two = input
                .stream()
                .map(DayThree::findLargestTwo)
                .peek(IO::println)
                .reduce(0L, Long::sum);

        IO.println("Answer first: %d | second: %d".formatted(ans_one, ans_two));
    }
}

