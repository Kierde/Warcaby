public class BoardSquare {

    int row;
    int col;

    public BoardSquare(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String toString() {

        String s = "row: " + row + " col: " + col;
        return s;
    }
}