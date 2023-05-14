
import java.security.cert.TrustAnchor;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // Warcaby angielskie (warcaby amerykańskie, czekersy)
        String regex = "^[0-7]{2} [0-7]{2}$";
        Player player1 = new Player(Player.Side.WHITE, "Biały");
        // Player player2 = new Player(Player.Side.BLACK, "Czarny");
        MinMaxBot bot = new MinMaxBot(Player.Side.BLACK, 3);
        Board b = new Board();
        // b.testBoard();
        System.out.println(b);
        boolean gameEnded = false;
        boolean turnWhite = true;
        Scanner moveScanner = new Scanner(System.in);
        String wynikGry = null;

        while (!gameEnded) {

            if (turnWhite) {
                System.out.println(player1);
                String move = moveScanner.nextLine();

                if (move.matches(regex)) {
                    BoardSquare start = new BoardSquare(Character.getNumericValue(move.charAt(0)),
                        Character.getNumericValue(move.charAt(1)));
                BoardSquare end = new BoardSquare(Character.getNumericValue(move.charAt(3)),
                        Character.getNumericValue(move.charAt(4)));
                wynikGry = player1.makeMove(new Move(start, end), b);
                turnWhite = false;
            } else {
                System.out.println("Zły ruch!");
                turnWhite = true;
            }  
            } else {
                System.out.println("Tura czarnych");
                wynikGry = bot.makeMove(b);
                turnWhite = true;
            }
            System.out.println(b);

            if (wynikGry != null) {
                            if (wynikGry.equals("Zly ruch") || wynikGry.equals("Figura nie jest twoja lub wybrałeś puste pole!")) {
                System.out.println("Zły ruch - wprowadz poprawny");

                if (player1.side == Player.Side.BLACK) {
                    turnWhite=false;
                }else{
                    turnWhite=true;
                }        
            }
            if (wynikGry.equals("Białe wygrały")) {
                System.out.println("Białe wygrały!");
                gameEnded = true;
            }
            if (wynikGry.equals("Czarne wygrały")) {
                System.out.println("Czarne wygrały!");
                gameEnded = true;
            }
            }
        }
    }
}
