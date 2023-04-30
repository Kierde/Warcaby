import java.util.ArrayList;

import javax.lang.model.util.SimpleElementVisitor6;

public class Board {

    Figures[][] board;
    int numWhitePawns;
    int numBlackPawns;
    int numWhiteKings;
    int numBlackKings;

    private enum Figures {
        WHITE, BLACK, BLACK_KING, WHITE_KING, EMPTY;
    }

    public Board() {
        beginStateOfBoard();
    }

    public void beginStateOfBoard() {

        board = new Figures[8][8];
        numWhitePawns = 12;
        numBlackPawns = 12;
        numWhiteKings = 0;
        numBlackKings = 0;

        for (int i = 0; i < 8; i++) {

            Figures figure = Figures.EMPTY;

            int begin = 0;

            if (i % 2 == 0)
                begin = 1;

            if (i <= 2)
                figure = Figures.BLACK;
            if (i >= 5)
                figure = Figures.WHITE;

            for (int j = begin; j < 8; j = j + 2) {
                board[i][j] = figure;
            }
        }
        fillEmptyOnBoard();
    }

    public void fillEmptyOnBoard() {

        Figures empty = Figures.EMPTY;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (board[i][j] == null)
                    board[i][j] = empty;
            }
        }
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {

            stringBuilder.append(i + " ");

            for (int j = 0; j < 8; j++) {

                if (board[i][j] == Figures.BLACK) {
                    stringBuilder.append("b|");
                } else if (board[i][j] == Figures.WHITE) {
                    stringBuilder.append("w|");
                } else if (board[i][j] == Figures.EMPTY) {
                    stringBuilder.append("_|");
                } else if (board[i][j] == Figures.BLACK_KING) {
                    stringBuilder.append("B|");
                } else {
                    stringBuilder.append("W|");
                }
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("  ");
        for (int k = 0; k < 8; k++) {
            stringBuilder.append(k + " ");
        }
        return stringBuilder.toString();
    }

    Figures getFigure(int row, int col) {
        return board[row][col];
    }

    public String makeMove(Move move, Player.Side side) {

        BoardSquare start = move.startSquare;
        BoardSquare end = move.endBoardSquare;

        Figures curr = getFigure(start.row, start.col);

        if (isMovingOwnFigure(curr, side) && curr != Figures.EMPTY) {
            return "To twoja figura";

        } else {
            return "To nie twoja figura";
        }
    }

    public boolean isMovingOwnFigure(Figures figure, Player.Side side) {

        if (side == Player.Side.WHITE && figure != Figures.WHITE && figure != Figures.WHITE_KING)
            return false;
        if (side == Player.Side.BLACK && figure != Figures.BLACK && figure != Figures.BLACK_KING)
            return false;
        return true;
    }

    public ArrayList<Move> normalProperMovesForFigure(int row, int col, Player.Side side) {

        ArrayList<Move> normalProperMoves = new ArrayList<Move>();

        Figures figure = getFigure(row, col);
        BoardSquare starBoardSquare = new BoardSquare(row, col);

        if (side == Player.Side.WHITE) {
            if (figure == Figures.WHITE) {

                int endRow = row - 1;
                int colLeft = col - 1;
                int colRight = col + 1;

                if (endRow >= 0) {
                    if (colLeft >= 0 && getFigure(endRow, colLeft) == Figures.EMPTY) {
                        normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colLeft))));
                    }
                    if (colRight < 8) {
                        normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colRight))));
                    }
                }
            }
        } else {
            if (figure == Figures.BLACK) {

            }
        }

        if (figure == Figures.BLACK_KING || figure == Figures.WHITE_KING) {

        }

        return normalProperMoves;
    }

}
