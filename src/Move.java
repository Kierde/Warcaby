public class Move {

    BoardSquare startSquare;
    BoardSquare endBoardSquare;

    public Move(BoardSquare start, BoardSquare end) {
        this.startSquare = start;
        this.endBoardSquare = end;
    }

    public String toString() {

        String s = "start row: " + startSquare.row + " start col: " + startSquare.col + " and stop row: "
                + endBoardSquare.row + " stop col: " + endBoardSquare.col;
        return s;
    }

}
