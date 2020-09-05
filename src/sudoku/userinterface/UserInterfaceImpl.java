package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;

/**
 * Now building the actual user interface which takes bulk of the time
 * The EventHandler and KeyEvent come from Javafx.. That's actually how we listen for inputs from the keyboard for the user interface.
 * We thus implement methods from the interfaces
 *
 * Again Stage comes from Javafx, and is basically like the background window for the apllication.
 * Group is more like a div (talking from the html/css perspective).. Its a container of some kind(View group from Android perspective)
 */
public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {
    private final Stage stage;
    private final Group root;

    /**
     *And how do we keep track of 81 different text fields?
     * We just need to have a reference variable for every single TextField. So we store the references of all these TextFields within our HashMap, so we can use the hash function to retrieve and store them
     * If you observed, the equals and hashCode functions were overridden before now, and the SudokuTextField also created has x and y value
     */
    private HashMap<Coordinates,SudokuTextField> textFieldCoordinates;

    private  IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0,0,128);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242,241);
    private static final String SUDOKU = "Sudoku";

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
//Helper methods
            drawBackground(root);
            drawTitle(root);
            drawSudokuBoard(root);
            drawTextFields(root);
            drawGridLines(root);
            stage.show();

    }

    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index<8){
            int thickness;
            if (index==2 || index==5){
                thickness = 3;
            }else {
                thickness = 2;
            }
            //Rectangle class from javafx... Result after implementation would be a series of gridlines.
            Rectangle verticalLine = getLine(
                    xAndY + 64 *index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 *index,
                    thickness,
                    BOARD_X_AND_Y
            );

            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );
            index++;
        }

    }

    private Rectangle getLine(double x, double y, double height, double width) {
        // Styling the rectangle
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int xAndYDelta = 64;

        //O(n^2) Runtime Complexity
        for (int xIndex=0; xIndex < 9; xIndex++){
            for (int yIndex=0; yIndex < 9; yIndex++){
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);
                //Pulling this code outside now into a helper method
                styleSudokuTile(tile,x,y);

                //Listening for inputs from the user
                tile.setOnKeyPressed(this);

                //The user interface simple class implements the EventListener and EventHandler interface from javafx.
                textFieldCoordinates.put(new Coordinates(xIndex,yIndex),tile);

                root.getChildren().add(tile);
            }

        }
    }

    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        //Font class is from javafx
        Font numberFont = new Font(32);

        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
        //Background EMPTY makes it transparent
        tile.setBackground(Background.EMPTY);

    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235,690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawBackground(Group root) {
        //Scene is a kind of View group
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }


    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x,y));

        String value = Integer.toString(
                input
        );
        if (value.equals("0")) value ="";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex=0; xIndex<9; xIndex++){
            for (int yIndex=0; yIndex<9; yIndex++){
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex,yIndex));

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                if (value.equals("0")) value= "";

                tile.setText(
                        value
                );

                /**
                 * Method would be called when a new game is created. If the square is empty, we disable the TextField and set it to a less thick font. Otherwise the TextField is enabled with a thick font.
                 */
                if (game.getGameState() == GameState.NEW){
                    if (value.equals("")){
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    }else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        /**
         * When the game logic shows that the game has been completed properly, then we get here
         */
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult()==ButtonType.OK) listener.onDialogClick();

    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();

    }

    @Override
    public void handle(KeyEvent event) {
        //When a user enters a number inside the text box, the event pops up here, and is hence checked
        if (event.getEventType()==KeyEvent.KEY_PRESSED){
            if (event.getText().matches("[0-9]")){
                int value = Integer.parseInt(event.getText());
                handleInput(value,event.getSource());
            }else {
                if (event.getCode()== KeyCode.BACK_SPACE){
                    handleInput(0,event.getSource());
                }else {
                    ((TextField) event.getSource()).setText("");
                }
            }
        }

    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }
}
