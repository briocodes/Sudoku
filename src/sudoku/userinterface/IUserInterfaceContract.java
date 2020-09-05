package sudoku.userinterface;

import sudoku.problemdomain.SudokuGame;

public interface IUserInterfaceContract {

    interface EventListener{
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
    }
    //The EventListener is like the presenter or controller part of the application that binds to the user interface
    interface View {
        void setListener(IUserInterfaceContract.EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String Message);
        void showError(String message);

    }
}
