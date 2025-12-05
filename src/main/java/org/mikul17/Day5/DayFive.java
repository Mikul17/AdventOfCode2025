package org.mikul17.Day5;

import org.mikul17.utils.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class DayFive {

    static class Range{
        long min;
        long max;

        public Range(long min, long max){
            this.min = min;
            this.max = max;
        }

        public Range(){
            this.min = 0;
            this.max = 0;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public void setMax(long max) {
            this.max = max;
        }

        @Override
        public String toString() {
            return "%s - %s".formatted(min, max);
        }
    }

    static Optional<Range> isOverlap(Range first, Range second){
        Range combined = new Range();

        if(second.min >= first.min && second.min <= first.max){
            combined.setMin(Math.min(first.min, second.min));
            combined.setMax(Math.max(first.max, second.max));
            return Optional.of(combined);
        }
        return Optional.empty();
    }

    static List<Range> prepareData(List<Range> og){
        int i =0;
        while(i<og.size() -1){
            Range first = og.get(i);
            Range second = og.get(i+1);
            Optional<Range> combinedMaybe = isOverlap(first, second);
            if(combinedMaybe.isEmpty()) {
                i++;
            }else{
                og.remove(i);
                og.remove(i);
                og.add(i,combinedMaybe.get());
            }
        }
        return og;
    }

    static Long solution(List<Range> ranges){
       long counter = 0L;
       for(Range range : ranges){
           counter += 1 + range.max - range.min;
       }
        return counter;
    }

    static Long solution(List<Long> inputs, List<Range> ranges){
        Long counter = 0L;
        for(Long i : inputs){
            for(Range r : ranges){
                if(i >= r.min && i<= r.max){
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }


    static void main() throws IOException {
        var lines = Files.lines(PathUtils.getFilePath(DayFive.class, "input.txt")).toList();

        List<Range> ranges = new ArrayList<>();
        List<Long> input = new ArrayList<>();

        boolean isPastSeparator = false;

        for(String line : lines){
            if(line.isBlank()){
                isPastSeparator = true;
                continue;
            }
            if(!isPastSeparator){
                int dashIndex = line.indexOf("-");
                Long min = Long.parseLong(line.substring(0, dashIndex));
                Long max = Long.parseLong(line.substring(dashIndex+1));
                Range range = new Range(min, max);
                ranges.add(range);
            }else{
                input.add(Long.parseLong(line));
            }
        }

        ranges.sort(Comparator.comparingLong(a -> a.min));
        List<Range> preparedData = prepareData(ranges);

        long ans = solution(input, ranges);
        long start = System.nanoTime();
        long ansTwo = solution(preparedData);
        long end = System.nanoTime();
        IO.println("Solution first: %d | second: %d (duration: %d ns)".formatted(ans, ansTwo, (end - start)));
    }
}
