import java.util.ArrayList;
import java.util.Random;

public class MinMaxBot {

    Player.Side side;
    int depth;

    public MinMaxBot(Player.Side side, int depth) {
        this.side = side;
        this.depth = depth;
    }

    public String makeMove(Board board) {
        Move m = minimaxStart(board, depth, side, true);
        return board.makeMove(m, side);
    }

    private Move minimaxStart(Board board, int depth, Player.Side side, boolean maximizingPlayer) {

        ArrayList<Move> validMoves = new ArrayList<Move>();

        validMoves.addAll(board.getAllTakeMoves(side));

        if (validMoves.size() == 0) {
            validMoves.addAll(board.getAllNormalMoves(side));
        }

        ArrayList<Double> heuristics = new ArrayList<Double>();

        Board tempBoard = null;
        for (int i = 0; i < validMoves.size(); i++) {
            tempBoard = board.cloneBoard();
            tempBoard.makeMove(validMoves.get(i), side);
            heuristics.add(minimax(tempBoard, depth - 1, oppositeSide(side), !maximizingPlayer));
        }
        double maxHeuristics = Double.NEGATIVE_INFINITY;

        Random rand = new Random();
        for (int i = heuristics.size() - 1; i >= 0; i--) {
            if (heuristics.get(i) >= maxHeuristics) {
                maxHeuristics = heuristics.get(i);
            }
        }
        for (int i = 0; i < heuristics.size(); i++) {
            if (heuristics.get(i) < maxHeuristics) {
                heuristics.remove(i);
                validMoves.remove(i);
                i--;
            }
        }
        return validMoves.get(rand.nextInt(validMoves.size()));
    }

    private double minimax(Board board, int depth, Player.Side side, boolean maximizingPlayer) {

        if (depth == 0) {
            return boardValidation(board);
        }
        ArrayList<Move> validMoves = new ArrayList<Move>();
        validMoves.addAll(board.getAllTakeMoves(side));

        if (validMoves.size() < 0) {
            validMoves.addAll(board.getAllNormalMoves(side));
        }

        double initial = 0;
        Board tempBoard = null;
        if (maximizingPlayer) {
            initial = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < validMoves.size(); i++) {
                tempBoard = board.cloneBoard();
                tempBoard.makeMove(validMoves.get(i), side);
                double result = minimax(tempBoard, depth - 1, oppositeSide(side), !maximizingPlayer);
                initial = Math.max(result, initial);

            }
        } else {
            initial = Double.POSITIVE_INFINITY;
            for (int i = 0; i < validMoves.size(); i++) {
                tempBoard = board.cloneBoard();
                tempBoard.makeMove(validMoves.get(i), side);
                double result = minimax(tempBoard, depth - 1, oppositeSide(side), !maximizingPlayer);
                initial = Math.min(result, initial);

            }
        }
        return initial;
    }

    private double boardValidation(Board b) {
        double kingWeight = 1.2;
        double result = 0;
        if (side == Player.Side.WHITE)
            result = b.getNumWhiteKingPieces() * kingWeight + b.getNumWhiteNormalPieces() - b.getNumBlackKingPieces() *
                    kingWeight -
                    b.getNumBlackNormalPieces();
        else
            result = b.getNumBlackKingPieces() * kingWeight + b.getNumBlackNormalPieces() - b.getNumWhiteKingPieces() *
                    kingWeight -
                    b.getNumWhiteNormalPieces();
        return result;
    }

    private Player.Side oppositeSide(Player.Side side) {
        if (side == Player.Side.BLACK)
            return Player.Side.WHITE;
        return Player.Side.BLACK;
    }
}