package sudoku.problemdomain;

import java.util.Objects;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * The equals method comes from the object class in Java, for which every class extends from the object implicitly. The essence is to be able to store this Coordinate object in a HashMap. We are going to be using them as keys to keep track of the inline elements.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || getClass() !=getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }
    /**
     * Implementing a hashCode function. (A hashCode is a unique identifier generated from some sort of data that is given). The Objects.hash(), generates a unique identifier for the x and y values
     */
    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
}
