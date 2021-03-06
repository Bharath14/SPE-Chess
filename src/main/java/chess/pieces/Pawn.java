package chess.pieces;

import chess.Cell;
import chess.Color;
import chess.Position;

import java.util.ArrayList;


public class Pawn extends Piece
{
    private boolean RecentMoveWasJump ;
    private int isMoved;
    private Cell enpasantCell;
    private boolean enpasantmove = false;
    public Pawn(Color color, int life, int isMoved, Position position)
    {
        super(PieceType.PAWN,color, life, position, 10);
        this.isMoved = isMoved;
        //this.empasantmove = empasantmove;
        //this.empasantCell = empasantCell;
    }

    public Pawn(Pawn piece){
        this(piece.getColor(), piece.getLife(),piece.getIsMoved(), piece.getPosition());
    }

    public void setRecentMoveWasJump(boolean newValue)
    {
        this.RecentMoveWasJump = newValue;
    }

    public boolean getRecentMoveWasJump()
    {
        return this.RecentMoveWasJump;
    }

    @Override
    public ArrayList<Cell> legalMoves(Cell cells[][]) {
        ArrayList<Cell> legalmoves = new ArrayList<Cell>();
        Position startPosition = this.getPosition();
        int[] X_Direction = {0,0,1,-1,-1,1};
        int[] Y_Direction = {1,2,1,1,0,0};
        int x = startPosition.getXCoordinate();
        int y = startPosition.getYCoordinate();
        for(int i =0;i<6;i++) {
            int destx = x;
            int desty = y;
            if (this.getColor() == Color.WHITE) {
                destx += X_Direction[i];
                desty += Y_Direction[i];
            } else {
                destx += X_Direction[i];
                desty -= Y_Direction[i];
            }
            if (destx >= 0 && destx < 8 && desty >= 0 && desty < 8) {
                if (i == 0) {
                    Cell destcell = cells[destx][desty];
                    Piece destpiece = destcell.getPiece();
                    if (destpiece == null) {
                        legalmoves.add(destcell);
                    }
                } else if (i == 1 && this.getIsMoved() == 0) {
                    Cell destcell1 = cells[destx][desty];
                    Piece destpiece1 = destcell1.getPiece();
                    Cell destcell;
                    Piece destpiece;
                    if (this.getColor() == Color.WHITE) {
                        destcell = cells[destx][desty - 1];
                        destpiece = destcell.getPiece();
                    } else {
                        destcell = cells[destx][desty + 1];
                        destpiece = destcell.getPiece();
                    }
                    if (destpiece1 == null && destpiece == null) {
                        legalmoves.add(destcell1);
                    }
                } else if (i == 2 || i == 3) {
                    if (destx >= 0 && destx < 8 && desty >= 0 && desty < 8) {
                        Cell destcell2 = cells[destx][desty];
                        Piece destpiece2 = destcell2.getPiece();
                        if (destpiece2 != null && destpiece2.getColor() != this.getColor()) {
                            legalmoves.add(destcell2);
                        }
                    }
                } else if (i > 3) {
                    if (destx >= 0 && destx < 8 && desty >= 0 && desty < 8) {
                        Cell destcell3 = cells[destx][desty];
                        Piece destpiece3 = destcell3.getPiece();
                        if (destpiece3 != null && destpiece3.getColor() != this.getColor() && destpiece3 instanceof Pawn) {
                            if (((Pawn) destpiece3).getRecentMoveWasJump() == true) {
                                if (this.getColor() == Color.WHITE) {
                                    legalmoves.add(cells[destx][desty + 1]);
                                } else {
                                    legalmoves.add(cells[destx][desty - 1]);
                                }

                                this.enpasantmove = true;
                                this.enpasantCell = destcell3;
                            }
                        }
                    }
                }
            }
        }
        return legalmoves;
    }

    public int getIsMoved()
    {
        return this.isMoved;
    }

    public void setIsMoved(int newValue)
    {
        this.isMoved = newValue;
    }

    public boolean getEnpasantmove()
    {
        return this.enpasantmove;
    }

    public Cell getEnpasantcell()
    {
        return this.enpasantCell;
    }

    public boolean checkPawnPromotion(Cell source)
    {
        if(source.getPiece().getType() != PieceType.PAWN)
        {
            return false;
        }
        else
        {
            if((source.getPiece().getColor() == Color.WHITE && source.getPosition().getYCoordinate() == 6)||
                    (source.getPiece().getColor() == Color.BLACK && source.getPosition().getYCoordinate() == 1))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public void setEnpasantmove(boolean newvalue)
    {
        this.enpasantmove = newvalue;
    }
    @Override
    public String toCharacter(){
        if(this.color==Color.WHITE) {
            return "P";
        }
        else{
            return "p";
        }
    }
}
