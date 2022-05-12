package chess.players;

import chess.Cell;
import chess.Color;

import chess.game_.Game;
import chess.game_.GameUtils;
import chess.pieces.Pawn;
import chess.pieces.PieceType;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Human extends Player {

    private static final Logger logger = LogManager.getLogger(Game.class);
    public Human(Color color) {
        super(PlayerType.Human,color);
    }
    public Human(Human player){
        this(player.getColor());
    }


    @Override
    public boolean move(Game game, String pieceType) {
        Cell source = this.getSource();
        Cell dest = this.getDest();

        ArrayList<Cell> moves = source.getPiece().legalMoves(game.getBoard().getCells());
        List<Pair<Cell, Cell>> allmoves = new ArrayList<Pair<Cell, Cell>>();
        for (Cell cell : moves) {
            Pair<Cell, Cell> move = new Pair<Cell, Cell>(source, cell);
            allmoves.add(move);
        }
        //System.out.println("legal move:" + allmoves.size());
        List<Pair<Cell, Cell>> filteredmoves = GameUtils.allFilteredMoves(game, allmoves);
        //System.out.println(filteredmoves.size());

        for (Pair<Cell, Cell> move : filteredmoves) {
            //System.out.println(dest.toCharacter());
            //System.out.println(move.getValue().toCharacter());
            if (move.getValue() == dest) {
                if(source.getPiece().getType()== PieceType.PAWN && ((Pawn)(source.getPiece())).checkPawnPromotion(source) && game.getBlackPlayer() instanceof Human && game.getWhitePlayer() instanceof Human) {
                    game.playTurnPromotion(source,dest,pieceType);
                }
                else {
                    game.playTurn(source, dest);
                }
                logger.info(game.getCurrentTurn().getColor().toString()+"True");
                    return true;
            }
        }

        for (Pair<Cell, Cell> move : allmoves){
            if (move.getValue() == dest) {
                if(GameUtils.isInCheck(game.getBoard().getCells(), game.getCurrentTurn().getColor())){
                    System.out.println("Your king is in check! Save him.");
                    logger.info(game.getCurrentTurn().getColor().toString()+"False");
                    return false;
                }
                System.out.println("The move made puts king in danger! Please try another move.");
                logger.info(game.getCurrentTurn().getColor().toString()+"False");
                return false;
            }
        }

        System.out.println("Selected piece can't reach the destination! Please try again.");
        logger.info(game.getCurrentTurn().getColor().toString()+"False");
        return false;
    }

}