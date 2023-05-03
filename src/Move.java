public class Move {

    BoardSquare startSquare;
    BoardSquare endBoardSquare;

    public Move(BoardSquare start, BoardSquare end) {
        this.startSquare = start;
        this.endBoardSquare = end;
    }

    @Override
    public String toString() {

        String s = "start row: " + startSquare.row + " start col: " + startSquare.col + " and stop row: "
                + endBoardSquare.row + " stop col: " + endBoardSquare.col;
        return s;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this)
            return true;

        if (!(obj instanceof Move))
            return false;

        Move move = (Move) obj;

        if (startSquare.equals(move.startSquare) && endBoardSquare.equals(move.endBoardSquare))
            return true;
        return false;
    }

}
