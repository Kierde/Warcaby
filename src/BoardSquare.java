public class BoardSquare {

    public int row;
    public int col;

    public BoardSquare(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String toString() {

        String s = "row: " + row + " col: " + col;
        return s;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (!(obj instanceof BoardSquare))
            return false;

        BoardSquare br = (BoardSquare) obj;

        if (Integer.compare(row, br.row) == 0 && Integer.compare(col, br.col) == 0)
            return true;
        return false;
    }

}