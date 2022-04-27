package chessgame.players;

import chessgame.Color;

public class PlayerFactory {
    public static Player createPlayer(Player original) {
        Player copy;
        if (original != null) {
            if(original.getType() == PlayerType.Minimax)
            {
                copy = new Minimax((Minimax) original);
            }
            else if(original.getType() == PlayerType.AlphaBeta)
            {
                copy = new AlphaBeta((AlphaBeta) original);
            }
            else if(original.getType() == PlayerType.Human)
            {
                copy = new Human((Human) original);
            }
            else
            {
                copy = null;
            }
        } else {
            copy = null;
        }
        return copy;
    }

    public static Player createPlayerfromstring (String type, Color color) {
        Player newPlayer;
        if(type.equals("HUMAN"))
        {
            newPlayer = new Human(color);
        }
        else if(type.equals("ALPHABETA"))
        {
            newPlayer = new AlphaBeta(color);
        }
        else if(type.equals("MINIMAX"))
        {
            newPlayer = new Minimax(color);
        }
        else
        {
            newPlayer = null;
        }
        return newPlayer;
    }
}