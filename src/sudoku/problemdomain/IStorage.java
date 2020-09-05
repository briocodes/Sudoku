package sudoku.problemdomain;

import java.io.IOException;

//Essence of using an interface is to design parts of the application upfront ahead of time (Design by contract). IOExceptions are also a necessary add since this is an I/O device
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
