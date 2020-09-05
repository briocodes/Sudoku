package sudoku.userinterface.logic;

import sudoku.computationlogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

//The ControlLogic class is a controller representer, and basically manages the interactions between the user, the user interface and the backend of the application(the computation logic)

public class ControlLogic implements IUserInterfaceContract.EventListener {
    //It is necessary when working on the controller, presenter or view model class,to always communicate with the backend and also the frontend(View class) through interfaces. If interfaces are not to be used anywhere else, its expedient to use them across very large and important architectural boundaries across any application. These are solid principles that help you design your application upfront without worrying about implementation.It's not a waste of time. If we are to change the Storage implementation, we can do that quickly without having to cause any issues in this control logic class.
    private IStorage storage;
    private IUserInterfaceContract.View view;

    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        //When the user enters a number, we write that to the game storage
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;

            gameData = new SudokuGame(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
            );

            storage.updateGameData(gameData);
            view.updateSquare(x,y,input);

            if (gameData.getGameState()== GameState.COMPLETE);
            view.showDialog(Messages.GAME_COMPLETE);

        }catch (IOException e){
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(
                    GameLogic.getNewGame()
            );

            view.updateBoard(storage.getGameData());
        }catch (IOException e){
            view.showError(Messages.ERROR);
        }
    }
}
