package org.mikul17.Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;

import static org.mikul17.utils.PathUtils.getFilePath;

public class DayOne {

    private static int counterOne = 0;
    private static int counterTwo = 0;


    private static int rotateClockPartTwo(String direction, Integer rotation, Integer currentDial) {
        if(direction.equals("L")) {
            if(currentDial == 0) {
                counterTwo += rotation / 100;
                currentDial = 100 - (rotation % 100);
                if(currentDial == 100) currentDial = 0;
            } else {
                int threshold = currentDial;
                if(rotation >= threshold) {
                    int remaining = rotation - threshold;
                    counterTwo += 1;
                    counterTwo += remaining / 100;
                    remaining = remaining % 100;
                    currentDial = 100 - remaining;
                    if(currentDial == 100) currentDial = 0;
                } else {
                    currentDial = currentDial - rotation;
                }
            }
        } else {
            if(currentDial == 0) {
                counterTwo += rotation / 100;
                currentDial = rotation % 100;
            } else {
                int stepsTo100 = 100 - currentDial;
                if(rotation >= stepsTo100) {
                    int remaining = rotation - stepsTo100;
                    counterTwo += 1;
                    counterTwo += remaining / 100;
                    currentDial = remaining % 100;
                } else {
                    currentDial = currentDial + rotation;
                }
            }
        }

        return currentDial;
    }

    private static int rotateClockPartOne(String direction, Integer rotation, Integer currentDial) {
        int factor = direction.equals("L") ? -1 : 1;
        currentDial += rotation * factor;
        currentDial %= 100;

        if (currentDial < 0) {
            currentDial += 100;
        }

        if (currentDial == 0) {
            counterOne++;
        }

        return currentDial;
    }


    static void main() throws IOException {
        var input = Files.lines(getFilePath(DayOne.class, "input.txt")).toList();

        AtomicReference<Integer> currentDial = new AtomicReference<>(50);
        AtomicReference<Integer> currentDialTwo = new AtomicReference<>(50);

        input.forEach((rotation) -> {
            String direction = rotation.substring(0, 1);
            Integer rotationValue = Integer.parseInt(rotation.substring(1));
            currentDial.set(rotateClockPartOne(direction, rotationValue, currentDial.get()));
            currentDialTwo.set(rotateClockPartTwo(direction, rotationValue, currentDialTwo.get()));
        });

        IO.println("Solution part 1: %d | part 2 %d".formatted(counterOne, counterTwo));
    }
}
