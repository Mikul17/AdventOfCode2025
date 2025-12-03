package org.mikul17.DayTwo;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.mikul17.utils.PathUtils.getFilePath;

public class DayTwo {

    static class Range {
        Long start;
        Long end;

        public Range(Long start, Long end){
            this.start = start;
            this.end = end;
        }
    }

    private static boolean isPalindrome(String input){
        int s = 0;
        int e = (input.length()) / 2;
        while (e != input.length()){
            char first = input.charAt(s);
            char second = input.charAt(e);
            if( first != second ){
                return false;
            }
            s++;
            e++;
        }
        return true;
    }


    private static Long findPatterns(Range range){
        long solution = 0L;
        int counter = 0;
        for(Long num = range.start ; num<=range.end ; num++){
            String current = num.toString();
            if(current.length() %2 != 0){
                continue;
            }
            if(isPalindrome(current)){
                solution += num;
                IO.println(num);
                counter++;
            }
        }
        IO.println("R: %d - %d | Count %d | %d".formatted(range.start, range.end, counter, solution));
        return solution;
    }

    private static Boolean findOccurrence(String current){
        if(current.length() == 1){
            return false;
        }

        int maxPatternLength = current.length() /2;

        for(int i = 1 ; i<= maxPatternLength; i++) {
            if(current.length() % i != 0){
                continue;
            }

            String pattern = current.substring(0, i);
            boolean isValid = true;

            for(int j = i; j < current.length(); j += i){
                String compPart = current.substring(j, j + i);
                if(!compPart.equals(pattern)){
                    isValid = false;
                    break;
                }
            }
            if(isValid){
                return true;
            }
        }
        return false;
    }

    private static Long findPatternsTwo(Range range){
        long solution = 0L;
        int counter = 0;
        for(Long num = range.start ; num<=range.end ; num++) {
            String current = num.toString();
            if(findOccurrence(current)){
                solution+=num;
                counter++;
                IO.println(num);
            }
        }
        IO.println("R: %d - %d | Count %d | %d".formatted(range.start, range.end, counter, solution));
        return solution;
    }


    static void main() throws IOException {
        List<Range> input = Files.lines(getFilePath(DayTwo.class, "input.txt")).map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .map(rangeStr -> {
                    String[] parts = rangeStr.split("-");
                    Long first = Long.parseLong(parts[0]);
                    Long second = Long.parseLong(parts[1]);
                    return new Range(first, second);
                })
                .toList();

        Long ansOne = input.stream().map(DayTwo::findPatterns).reduce(0L, Long::sum);

        Long ansTwo = input
                .stream()
                .map(DayTwo::findPatternsTwo)
                .reduce(0L, Long::sum);

        IO.println("Answer: part1 : %d | part2: %d".formatted(ansOne, ansTwo));
    }


}
