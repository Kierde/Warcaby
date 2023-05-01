import javafx.print.PrintQuality;

public class App {
    public static void main(String[] args) throws Exception {

        // Warcaby angielskie (warcaby amerykańskie, czekersy)

        Board b = new Board();
        b.testBoard();
        System.out.println(b);
        b.TakeMoves(5, 4, Player.Side.WHITE);

        //b.normalProperMovesForFigure(1, 0, Player.Side.BLACK);

        // b.getAllNormalMoves(Player.Side.WHITE);

        // System.out.println(b.toString());

        // Player player = new Player(Player.Side.BLACK, "Player one");
        // Move move = new Move(new BoardSquare(4, 3), new BoardSquare(4, 1));

        // System.out.println(move);
        // System.out.println(player.makeMove(move, b));

    }
}
