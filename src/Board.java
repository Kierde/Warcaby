import java.io.Console;
import java.util.ArrayList;

public class Board {

    Figures[][] board;
    int numWhitePawns;
    int numBlackPawns;
    int numWhiteKings;
    int numBlackKings;

    public enum Figures {
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

            ArrayList<Move> properMoves = new ArrayList<Move>();
            properMoves.addAll(getAllTakeMoves(side));

            // sprwdzanie czy bicie jest możliwe - bicie obowiązkowe
            if (properMoves.size() > 0) {

                if (properMoves.contains(move)) {

                    board[end.row][end.col] = curr;
                    BoardSquare squareTook = new BoardSquare((start.row + end.row) / 2, (start.col + end.col) / 2);
                    Figures took = getFigure(squareTook.row, squareTook.col);

                    board[squareTook.row][squareTook.col] = Figures.EMPTY;
                    board[start.row][start.col] = Figures.EMPTY;

                    if (took == Figures.BLACK) {
                        numBlackPawns--;
                    } else if (took == Figures.WHITE) {
                        numWhitePawns--;
                    } else if (took == Figures.BLACK_KING) {
                        numBlackKings--;
                    } else {
                        numWhiteKings--;
                    }
                } else {
                    return "Zly ruch";
                }
            } else {
                // normalny ruch
                properMoves.addAll(getAllNormalMoves(side));
                if (properMoves.contains(move)) {

                    board[end.row][end.col] = curr;
                    board[start.row][start.col] = Figures.EMPTY;
                } else {
                    return "Zly ruch";
                }
            }
            if (properMoves.size() > 0) {
                if (side == Player.Side.WHITE && curr != Figures.WHITE_KING) {
                    if (board[end.row][end.col] == board[0][end.col]) {
                        numWhiteKings++;
                        numWhitePawns--;
                        board[end.row][end.col] = Figures.WHITE_KING;
                    }
                } else {
                    if (board[end.row][end.col] == board[7][end.col] && curr != Figures.BLACK_KING) {
                        numBlackKings++;
                        numBlackPawns--;
                        board[end.row][end.col] = Figures.BLACK_KING;
                    }
                }
            }

            int sumOfFiguresWhite = numWhiteKings + numWhitePawns;
            int sumOfFiguresBlack = numBlackKings + numBlackPawns;
            ArrayList<Move> possibleMoves = new ArrayList<Move>();

            possibleMoves.addAll(getAllTakeMoves(oppositeSide(side)));
            possibleMoves.addAll(getAllNormalMoves(oppositeSide(side)));

            // sprawdzanie kto wygrał albo zbicie wszystkich figur albo brak ruchu
            // przeciwnika
            if (sumOfFiguresWhite == 0 || possibleMoves.size() == 0) {
                return "Czarne wygrały";
            }

            if (sumOfFiguresBlack == 0 || possibleMoves.size() == 0) {
                return "Białe wygrały";
            }
            return "Ruch poprawny";
        } else {
            return "Figura nie jest twoja lub wybrałeś puste pole!";
        }
    }

    public boolean isMovingOwnFigure(Figures figure, Player.Side side) {
        // w makeMove należy przed wywołaniem sprawdzić czy nie jest empty
        if (figure == Figures.EMPTY)
            return true;
        if (side == Player.Side.WHITE && figure != Figures.WHITE && figure != Figures.WHITE_KING)
            return false;
        if (side == Player.Side.BLACK && figure != Figures.BLACK && figure != Figures.BLACK_KING)
            return false;
        return true;
    }

    public Player.Side oppositeSide(Player.Side side) {

        Player.Side oppSite = null;

        if (oppSite == Player.Side.WHITE) {
            oppSite = Player.Side.BLACK;
        } else {
            oppSite = Player.Side.WHITE;
        }
        return oppSite;
    }

    public void testBoard() {

        numWhitePawns = 1;
        numBlackPawns = 1;
        numWhiteKings = 0;
        numBlackKings = 0;
        board = new Figures[8][8];

        board[3][3] = Figures.WHITE;
        board[2][4] = Figures.BLACK;

        fillEmptyOnBoard();
    }

    public ArrayList<Move> getAllNormalMoves(Player.Side side) {

        ArrayList<Move> allNormalMoves = new ArrayList<Move>();
        Figures figure;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                figure = getFigure(i, j);
                if (figure != Figures.EMPTY && ((side == side.WHITE && figure != Figures.BLACK_KING)
                        || (side == side.BLACK && figure != Figures.WHITE_KING))) {
                    allNormalMoves.addAll(normalProperMovesForFigure(i, j, side));
                }
            }
        }
        // for (int i = 0; i < allNormalMoves.size(); i++)
        // 5.println(allNormalMoves.get(i));
        return allNormalMoves;
    }

    public ArrayList<Move> normalProperMovesForFigure(int row, int col, Player.Side side) {

        ArrayList<Move> normalProperMoves = new ArrayList<Move>();

        Figures figure = getFigure(row, col);
        BoardSquare starBoardSquare = new BoardSquare(row, col);

        int endRow;
        int colLeft;
        int colRight;
        // dwie możliwości ruchu
        if (side == Player.Side.WHITE) {
            if (figure == Figures.WHITE) {

                endRow = row - 1;
                colLeft = col - 1;
                colRight = col + 1;

                if (endRow >= 0) {
                    if (colLeft >= 0 && getFigure(endRow, colLeft) == Figures.EMPTY) {
                        normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colLeft))));
                    }
                    if (colRight < 8 && getFigure(endRow, colRight) == Figures.EMPTY) {
                        normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colRight))));
                    }
                }
            }
        } else {
            if (figure == Figures.BLACK) {

                endRow = row + 1;
                colLeft = col - 1;
                colRight = col + 1;

                if (endRow < 8) {
                    if (colLeft >= 0 && getFigure(endRow, colLeft) == Figures.EMPTY) {
                        normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colLeft))));
                    }
                    if (colRight < 8 && getFigure(endRow, colRight) == Figures.EMPTY) {
                        normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colRight))));
                    }
                }
            }
        }
        // 4 możliwości ruchu
        if (figure == Figures.BLACK_KING || figure == Figures.WHITE_KING) {

            // do góry
            endRow = row - 1;
            colLeft = col - 1;
            colRight = col + 1;

            if (endRow >= 0) {
                if (colLeft >= 0 && getFigure(endRow, colLeft) == Figures.EMPTY) {
                    normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colLeft))));
                }
                if (colRight < 8 && getFigure(endRow, colRight) == Figures.EMPTY) {
                    normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colRight))));
                }
            }
            // do dołu
            endRow = row + 1;

            if (endRow < 8) {
                if (colLeft >= 0 && getFigure(endRow, colLeft) == Figures.EMPTY) {
                    normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colLeft))));
                }
                if (colRight < 8 && getFigure(endRow, colRight) == Figures.EMPTY) {
                    normalProperMoves.add((new Move(starBoardSquare, new BoardSquare(endRow, colRight))));
                }
            }
        }
        // Wypisanie możliwych ruchów
        // for (int i = 0; i < normalProperMoves.size(); i++) {
        // System.out.println(normalProperMoves.get(i));
        // }
        return normalProperMoves;
    }

    public ArrayList<Move> getAllTakeMoves(Player.Side side) {

        ArrayList<Move> allTakeMoves = new ArrayList<Move>();
        Figures figure;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                figure = getFigure(i, j);
                if (figure != Figures.EMPTY && ((side == side.WHITE && figure != Figures.BLACK_KING)
                        || (side == side.BLACK && figure != Figures.WHITE_KING))) {
                    allTakeMoves.addAll(takeMoves(i, j, side));
                }
            }
        }
        return allTakeMoves;
    }

    // ruchy bicia - obowiązkowe
    public ArrayList<Move> takeMoves(int row, int col, Player.Side side) {

        ArrayList<Move> takeMoves = new ArrayList<Move>();
        Figures figure = getFigure(row, col);
        BoardSquare starBoardSquare = new BoardSquare(row, col);

        int endRow;
        int colLeft;
        int colRight;

        int enemyRow;
        int enemyColLeft;
        int enemyColRight;

        if (side == Player.Side.WHITE) {
            if (figure == Figures.WHITE) {

                endRow = row - 2;
                colLeft = col - 2;
                colRight = col + 2;

                enemyRow = row - 1;
                enemyColLeft = col - 1;
                enemyColRight = col + 1;

                if (endRow >= 0) {
                    if (colLeft >= 0 && (getFigure(enemyRow, enemyColLeft) == Figures.BLACK
                            || getFigure(enemyRow, enemyColLeft) == Figures.BLACK_KING)
                            && getFigure(endRow, colLeft) == Figures.EMPTY) {
                        takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colLeft)));
                    }
                    if (colRight < 8 && (getFigure(enemyRow, enemyColRight) == Figures.BLACK
                            || getFigure(enemyRow, enemyColRight) == Figures.BLACK_KING)
                            && getFigure(endRow, colRight) == Figures.EMPTY) {
                        takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colRight)));
                    }
                }
            }
        } else {
            if (figure == Figures.BLACK) {
                endRow = row + 2;
                colLeft = col - 2;
                colRight = col + 2;

                enemyRow = row + 1;
                enemyColLeft = col - 1;
                enemyColRight = col + 1;

                if (endRow < 8) {
                    if (colLeft >= 0 && (getFigure(enemyRow, enemyColLeft) == Figures.WHITE
                            || getFigure(enemyRow, enemyColLeft) == Figures.WHITE_KING)
                            && getFigure(endRow, colLeft) == Figures.EMPTY) {
                        takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colLeft)));
                    }
                    if (colRight < 8 && (getFigure(enemyRow, enemyColRight) == Figures.WHITE
                            || getFigure(enemyRow, enemyColRight) == Figures.WHITE_KING)
                            && getFigure(endRow, colRight) == Figures.EMPTY) {
                        takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colRight)));
                    }
                }
            }
        }
        if (figure == Figures.WHITE_KING || figure == Figures.BLACK_KING) {

            endRow = row - 2;
            colLeft = col - 2;
            colRight = col + 2;

            enemyRow = row - 1;
            enemyColLeft = col - 1;
            enemyColRight = col + 1;

            if (endRow >= 0) {
                if (colLeft >= 0 && !isMovingOwnFigure(getFigure(enemyRow, enemyColLeft), side)
                        && getFigure(endRow, colLeft) == Figures.EMPTY) {
                    takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colLeft)));
                }
                if (colRight < 8 && !isMovingOwnFigure(getFigure(enemyRow, enemyColRight), side)
                        && getFigure(endRow, colRight) == Figures.EMPTY) {
                    takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colRight)));
                }
            }
            endRow = row + 2;
            enemyRow = row + 1;

            if (endRow < 8) {

                if (colLeft >= 0 && !isMovingOwnFigure(getFigure(enemyRow, enemyColLeft), side)
                        && getFigure(endRow, colLeft) == Figures.EMPTY) {
                    takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colLeft)));
                }
                if (colRight < 8 && !isMovingOwnFigure(getFigure(enemyRow, enemyColRight), side)
                        && getFigure(endRow, colRight) == Figures.EMPTY) {
                    takeMoves.add(new Move(starBoardSquare, new BoardSquare(endRow, colRight)));
                }
            }
        }

        // for (int i = 0; i < takeMoves.size(); i++)
        // System.out.println(takeMoves.get(i));
        return takeMoves;
    }

}
