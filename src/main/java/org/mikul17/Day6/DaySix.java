package org.mikul17.Day6;

import org.apache.commons.lang3.StringUtils;
import org.mikul17.utils.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DaySix {

    static Long solve(String[][] input){
      long sum = 0L;
      int rowCount = input.length;
      int colCount = input[0].length;

      for(int col = 0 ; col < colCount; col++){
          boolean operator = input[rowCount -1][col].equals("*");
          long smallSum = operator ? 1L : 0L;
          for(int row =0; row<rowCount-1; row ++){
              Long num = Long.parseLong(input[row][col]);
             if(operator){
                 smallSum *= num;
             }else{
                 smallSum += num;
             }
          }
          sum += smallSum;
      }

      return sum;
    }

    static int findPad(String[][] input){
        int rowCount = input.length;
        int colCount = input[0].length;
        int max=0;

        for(int col =0; col < colCount; col++){
            for(int row =0; row<rowCount -1; row++){
                int length = input[row][col].length();
                if(length > max){
                    max = length;
                }
            }
        }
        return max;
    }

    static Long solveTwo(String[][] input){
        long sum = 0L;
        int rowCount = input.length;
        int colCount = input[0].length;
        int pad = findPad(input);

        for(int col = colCount-1; col >= 0; col--){
            boolean operator = input[rowCount -1][col].equals("*");
            long[] sums = new long[pad];
            for(int row =0; row<rowCount-1; row ++){
                String currentString = input[row][col];
                for(int k =0; k<currentString.length(); k++){
                    long current = Long.parseLong(String.valueOf(currentString.charAt(k)));
                    if(operator){
                        sums[k] = sums[k] == 0 ? 1 : sums[k];
                        sums[k] *= current;
                    }else{
                        sums[k] += current;
                    }
                }
            }
            for(int k =0; k<pad; k++){
                if(operator){
                    sum *= sums[k];
                }else{
                    sum += sums[k];
                }
            }
        }
        return sum;
    }


    static void main() throws IOException {
        var input = Files.readAllLines(PathUtils.getFilePath(DaySix.class, "test.txt"));


        String[][] inputMappedFirst =
                input.stream().map((line) -> {
                    String[] split = line.strip().split("\\s+");
                    return split;
                }).toArray(String[][]::new);

        String[][] inputMappedSecond =
                input.stream().map((line) -> {
                    String[] result = new String[inputMappedFirst[0].length];
                    var test = line.toCharArray();
                    for(int i=0; i< test.length; i++){
                        StringBuilder sb = new StringBuilder();
                        while(test[i] != ' '){
                            sb.append(test[i]);
                        }
                    }
                })


        Long ans = solveTwo(inputMappedFirst);
        IO.println(ans);
    }
}
