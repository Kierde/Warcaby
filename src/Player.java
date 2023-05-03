public class Player {

    public Side side;
    public String playerName;

    public enum Side {
        BLACK, WHITE
    };

    public Player(Side side, String playerName) {

        this.side = side;
        this.playerName = playerName;
    }

    public String makeMove(Move move, Board board) {
        return board.makeMove(move, side);
    }

    public String toString() {
        return "Tura gracza: " + playerName;
    }

}
