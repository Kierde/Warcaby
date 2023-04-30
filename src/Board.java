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
}
