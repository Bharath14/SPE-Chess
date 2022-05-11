
import chessgame.Cell;
import chessgame.Color;
import chessgame.game_.Game;
import chessgame.game_.GameStatus;
import chessgame.players.Human;
import chessgame.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessGameTest {
    private Player w=new Human(Color.WHITE);
    private Player b=new Human(Color.BLACK);
    private Game g=new Game(w,b);
    private Cell[][] cells = g.getBoard().getCells();
    /*
        @Test
        public void testPawn(){

        }

        @Test
        public void testRook(){

        }

        @Test
        public void testKnight(){

        }

        @Test
        public void testBishop(){

        }

        @Test
        public void testQueen(){

        }

        @Test
        public void testKing(){

        }

        @Test
        public void testEnpassant(){

        }

        @Test
        public void testCastle(){

        }
    */
    @Test
    public void testCheckMate(){
        Player p=g.getCurrentTurn();
        Cell source=cells[5][1];
        p.setSource(source,g);
        Cell destination=cells[5][2];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][6];
        p.setSource(source,g);
        destination=cells[4][5];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[6][1];
        p.setSource(source,g);
        destination=cells[6][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[3][7];
        p.setSource(source,g);
        destination=cells[7][3];
        p.setDest(destination);
        p.move(g,"QUEEN");
        int ans=1;
        if(g.getGameStatus()==GameStatus.BLACKWINS){
            ans=0;
        }
        assertEquals(0,ans,"CheckMate to white");
    }
/*
    @Test
    public void testStaleMate(){

    }

    @Test
    public void testMoveWhenKingInCheck(){

    }

    @Test
    public void testMoveToSaveKingInCheck(){

    }

    @Test
    public void testMoveToLetKingBeInDanger(){

    }
    */

}