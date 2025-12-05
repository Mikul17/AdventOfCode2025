package org.mikul17.Day4;

import org.mikul17.utils.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DayFour {

    static int rows = 0;
    static int cols =0;

    /*******
     | (-1, -1) | (0, -1) | (1, -1) |
     | (-1, 0)  |  (0,0)  |  (1,0)  |
     | (-1, 1)  |  (0,1)  |  (1,1)  |
    *******/


    private static boolean applyMask(int[][] board, int currRow, int currCol){
        boolean isTopRow = currRow == 0;
        boolean isBottomRow = currRow == rows-1;
        boolean isFirstCol = currCol == 0;
        boolean isLastCol = currCol == cols -1;

        int startRow = isTopRow ? currRow : currRow -1;
        int endRow = isBottomRow ? currRow : currRow +1;
        int startCol = isFirstCol ? currCol : currCol -1;
        int endCol = isLastCol ? currCol : currCol +1;

        int counter = 0;

        for(int i=startRow; i<=endRow; i++){
            for(int j = startCol; j<=endCol; j++){
                if(i == currRow && j ==currCol){
                    continue;
                }
                if(board[i][j] == 1){
                    counter++;
                }
            }
        }

        return counter < 4;
    }


    private static int solve(int[][] board){
        int counter = 0;
        for(int row = 0; row < rows; row++){
            for (int col=0; col< cols; col++){
                if(board[row][col] == 1 && applyMask(board, row, col)){
                    counter++;

                    IO.println("X for board[%d][%d]".formatted(row,col));
                }
            }
        }

        return counter;
    }

    private static int solveTwo(int[][] board){
        int counter =0;
        boolean hasRemovedAny = true;
        while(hasRemovedAny){
            int removedCounter = 0;
            for(int row = 0; row < rows; row++){
                for (int col=0; col< cols; col++){
                    if(board[row][col] == 1 && applyMask(board, row, col)){
                        counter++;
                        removedCounter ++;
                        board[row][col] = 0;
                        IO.println("X for board[%d][%d]".formatted(row,col));
                    }
                }
            }
            if(removedCounter == 0){
                hasRemovedAny = false;
            }
        }
        return counter;
    }

    static void main() throws IOException {

        List<String> input = Files.lines(PathUtils.getFilePath(DayFour.class, "input.txt")).toList();

        rows = input.size();
        cols = input.getFirst().length();
        int[][] board = new int[rows][cols];

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                board[i][j] = input.get(i).charAt(j) == '@' ? 1 : 0;
            }
        }

        int solution = solve(board);
        int solutionTwo = solveTwo(board);

        IO.println("Solution part1: %d | part2: %d".formatted(solution, solutionTwo));
    }
}
