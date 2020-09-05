package sudoku.computationlogic;

import sudoku.problemdomain.SudokuGame;

public class SudokuUtilities {
    //The utility class has a utility/static function which would copy the values from one 2-dimensional sudoku array that's passed in, into another 2-dimensional array. And it also has a second function which will take in an old array and return a new array
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray){
        for (int xIndex=0; xIndex< SudokuGame.GRID_BOUNDARY; xIndex++){
            for (int yIndex=0; yIndex< SudokuGame.GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = newArray[xIndex][yIndex];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray){
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];

        for (int xIndex=0; xIndex<SudokuGame.GRID_BOUNDARY; xIndex++){
            for (int yIndex=0; yIndex<SudokuGame.GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
        return newArray;
    }
}
