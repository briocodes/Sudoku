package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.persistence.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {

    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            initialState = storage.getGameData();
        }catch (IOException e){
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        //Binding everything together and creating the UI control logic class
        IUserInterfaceContract.EventListener uiLogic =
                new ControlLogic(storage,userInterface);
        //Binding the ControlLogic class to our user interface
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
