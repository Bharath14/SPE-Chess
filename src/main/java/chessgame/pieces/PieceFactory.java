package chessgame.pieces;
import chessgame.Color;
import chessgame.Position;


public class PieceFactory {
    public static Piece createPiece (Piece original) {
        Piece copy;
        if(original != null) {
            if(original.getType() == PieceType.KING)
            {
                copy = new King((King) original);
            }
            else if(original.getType() == PieceType.QUEEN)
            {
                copy = new Queen((Queen) original);
            }
            else if(original.getType() == PieceType.ROOK)
            {
                copy = new Rook((Rook) original);
            }
            else if(original.getType() == PieceType.BISHOP)
            {
                copy = new Bishop((Bishop) original);
            }
            else if(original.getType() == PieceType.KNIGHT)
            {
                copy = new Knight((Knight) original);
            }
            else if(original.getType() == PieceType.PAWN)
            {
                copy = new Pawn((Pawn) original);
            }
            else {
                copy = null;
            }
        }
        else
        {
            copy = null;
        }
        return copy;
    }

    public static Piece createPromotionPiece (String type, Piece original) {
        Piece newPiece;
        Position originalPosition=original.getPosition();
        Color originalColor=original.getColor();
        if(type == "QUEEN")
        {
            newPiece = new Queen(originalColor,1,originalPosition);
        }
        else if(type == "ROOK")
        {
            newPiece = new Rook(originalColor,1,1,originalPosition);
        }
        else if(type == "BISHOP")
        {
            newPiece = new Bishop(originalColor,1,originalPosition);
        }
        else if(type == "KNIGHT")
        {
            newPiece = new Knight(originalColor,1,originalPosition);
        }
        else
        {
            newPiece = null;
        }
        return newPiece;
    }

}