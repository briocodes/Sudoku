package sudoku.problemdomain;

import java.io.IOException;

//Essence of using an interface is to design parts of the application upfront. (Design by contract).
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
