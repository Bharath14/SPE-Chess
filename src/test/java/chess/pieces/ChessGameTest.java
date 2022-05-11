package chess.pieces;

import chess.Cell;
import chess.Color;
import chess.game_.Game;
import chess.game_.GameStatus;
import chess.players.Human;
import chess.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameTest {
    private Player w;
    private Player b;
    private Game g;
    private Cell[][] cells;

    @Test
    public void testPawn(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[5][1];
        p.setSource(source,g);
        Cell destination=cells[5][2];
        p.setDest(destination);
        boolean valid=p.move(g,"PAWN");
        assertEquals(true,valid,"Can advance 2 steps");
    }

    @Test
    public void testRook(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[0][1];
        p.setSource(source,g);
        Cell destination=cells[0][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[1][6];
        p.setSource(source,g);
        destination=cells[1][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[0][3];
        p.setSource(source,g);
        destination=cells[1][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[2][7];
        p.setSource(source,g);
        destination=cells[1][6];
        p.setDest(destination);
        p.move(g,"BISHOP");

        p=g.getCurrentTurn();
        source=cells[0][0];
        p.setSource(source,g);
        destination=cells[0][6];
        p.setDest(destination);
        boolean valid=p.move(g,"ROOK");

        assertEquals(true,valid,"rook kills pawn");
    }

    @Test
    public void testKnight(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[1][0];
        p.setSource(source,g);
        Cell destination=cells[0][2];
        p.setDest(destination);
        boolean valid=p.move(g,"KNIGHT");
        assertEquals(true,valid,"knight movement is valid");
    }

    @Test
    public void testBishop(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[3][1];
        p.setSource(source,g);
        Cell destination=cells[3][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[1][7];
        p.setSource(source,g);
        destination=cells[2][5];
        p.setDest(destination);
        p.move(g,"KNIGHT");

        p=g.getCurrentTurn();
        source=cells[2][0];
        p.setSource(source,g);
        destination=cells[7][5];
        p.setDest(destination);
        boolean valid=p.move(g,"BISHOP");
        assertEquals(true,valid,"bishop movement is valid");
    }

    @Test
    public void testQueen(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[3][1];
        p.setSource(source,g);
        Cell destination=cells[3][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[1][7];
        p.setSource(source,g);
        destination=cells[2][5];
        p.setDest(destination);
        p.move(g,"KNIGHT");

        p=g.getCurrentTurn();
        source=cells[3][0];
        p.setSource(source,g);
        destination=cells[3][1];
        p.setDest(destination);
        boolean valid=p.move(g,"QUEEN");
        assertEquals(true,valid,"queen movement is valid");
    }

    @Test
    public void testKing(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[3][1];
        p.setSource(source,g);
        Cell destination=cells[3][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[1][7];
        p.setSource(source,g);
        destination=cells[2][5];
        p.setDest(destination);
        p.move(g,"KNIGHT");

        p=g.getCurrentTurn();
        source=cells[4][0];
        p.setSource(source,g);
        destination=cells[3][1];
        p.setDest(destination);
        boolean valid=p.move(g,"KING");
        assertEquals(true,valid,"king movement is valid");
    }

    @Test
    public void testEnpassant(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[4][1];
        p.setSource(source,g);
        Cell destination=cells[4][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[0][6];
        p.setSource(source,g);
        destination=cells[0][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][3];
        p.setSource(source,g);
        destination=cells[4][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[3][6];
        p.setSource(source,g);
        destination=cells[3][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][4];
        p.setSource(source,g);
        destination=cells[3][5];
        p.setDest(destination);
        boolean valid=p.move(g,"PAWN");
        assertEquals(true,valid,"king movement is valid");
    }

    @Test
    public void testCastle(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[3][1];
        p.setSource(source,g);
        Cell destination=cells[3][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[3][6];
        p.setSource(source,g);
        destination=cells[3][5];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[2][0];
        p.setSource(source,g);
        destination=cells[7][5];
        p.setDest(destination);
        p.move(g,"BISHOP");

        p=g.getCurrentTurn();
        source=cells[6][6];
        p.setSource(source,g);
        destination=cells[7][5];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[1][0];
        p.setSource(source,g);
        destination=cells[0][2];
        p.setDest(destination);
        p.move(g,"KNIGHT");

        p=g.getCurrentTurn();
        source=cells[3][5];
        p.setSource(source,g);
        destination=cells[3][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[3][0];
        p.setSource(source,g);
        destination=cells[3][2];
        p.setDest(destination);
        p.move(g,"QUEEN");

        p=g.getCurrentTurn();
        source=cells[7][5];
        p.setSource(source,g);
        destination=cells[7][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][0];
        p.setSource(source,g);
        destination=cells[2][0];
        p.setDest(destination);
        boolean valid=p.move(g,"KING");
        assertEquals(true,valid,"valid castling");

    }

    @Test
    public void testCheckMate(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
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
        if(g.getGameStatus()== GameStatus.BLACKWINS){
            ans=0;
        }
        assertEquals(0,ans,"CheckMate to white");
    }

    @Test
    public void testMoveWhenKingInCheck(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[3][1];
        p.setSource(source,g);
        Cell destination=cells[3][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][6];
        p.setSource(source,g);
        destination=cells[4][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][1];
        p.setSource(source,g);
        destination=cells[4][2];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[5][7];
        p.setSource(source,g);
        destination=cells[1][3];
        p.setDest(destination);
        p.move(g,"BISHOP");

        p=g.getCurrentTurn();
        source=cells[0][1];
        p.setSource(source,g);
        destination=cells[0][3];
        p.setDest(destination);
        boolean valid= p.move(g,"PAWN");
        assertEquals(false,valid,"move piece from (0,1) to (0,3) which is invalid");
    }

    @Test
    public void testMoveToSaveKingInCheck(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[3][1];
        p.setSource(source,g);
        Cell destination=cells[3][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][6];
        p.setSource(source,g);
        destination=cells[4][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][1];
        p.setSource(source,g);
        destination=cells[4][2];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[5][7];
        p.setSource(source,g);
        destination=cells[1][3];
        p.setDest(destination);
        p.move(g,"BISHOP");

        p=g.getCurrentTurn();
        source=cells[2][1];
        p.setSource(source,g);
        destination=cells[2][2];
        p.setDest(destination);
        boolean valid= p.move(g,"PAWN");
        assertEquals(true,valid,"valid move to save king from check");
    }

    @Test
    public void testMoveToLetKingBeInDanger(){
        w=new Human(Color.WHITE);
        b=new Human(Color.BLACK);
        g=new Game(w,b);
        cells = g.getBoard().getCells();
        Player p=g.getCurrentTurn();
        Cell source=cells[4][1];
        p.setSource(source,g);
        Cell destination=cells[4][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[4][6];
        p.setSource(source,g);
        destination=cells[4][4];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[7][1];
        p.setSource(source,g);
        destination=cells[7][2];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[5][7];
        p.setSource(source,g);
        destination=cells[1][3];
        p.setDest(destination);
        p.move(g,"PAWN");

        p=g.getCurrentTurn();
        source=cells[3][1];
        p.setSource(source,g);
        destination=cells[3][2];
        p.setDest(destination);
        boolean valid=p.move(g,"PAWN");
        assertEquals(false,valid,"valid move to save king from check");
    }
}