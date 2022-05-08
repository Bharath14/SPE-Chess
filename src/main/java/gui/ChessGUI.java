package gui;


import chessgame.*;
import chessgame.Cell;
import chessgame.game_.Game;
import chessgame.game_.GameUtils;
import chessgame.pieces.Pawn;
import chessgame.pieces.Piece;
import chessgame.pieces.PieceType;
import chessgame.players.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.stage.Window;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static chessgame.game_.GameStatus.*;


public class ChessGUI extends Application {
    private static Player playerWhite;
    private static Player playerBlack;
    private static Game game = new Game(playerWhite,playerBlack);
    private static Label whosTurn = new Label("Currently White: Human's turn");// + game.getCurrentTurn().toCharacter() + "'s turn.");
    //private static Label turnNumber = new Label("Turn: " + game.getTurn());xx
    //private static Label fiftyMove = new Label("Fifty-move Rule: " + game.getPeace());
    //private static Label repetition = new Label("Repetition: " + GameHelper.repetition(game));
    private static Label fen = new Label("FEN: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w");
    private static Scene startScene, mainScene;
    private static int fromX;
    private static int fromY;
    private static int toX;
    private static int toY;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) {
        // start of main scene
        Pane root = new Pane();
        // VBox for the right side with the info diplays and the input. The HBox is a
        // inside this VBox.
        VBox infoDisplay = new VBox();

        Rectangle blackBackground = new Rectangle(0, 0, 240, 80);
        blackBackground.setFill(Color.BLACK);
        Rectangle whiteBackground = new Rectangle(0, 1, 240, 80);
        whiteBackground.setFill(Color.WHITE);

        // text displays
        infoDisplay.getChildren().add(whosTurn);
        //infoDisplay.getChildren().add(turnNumber);
        //infoDisplay.getChildren().add(fiftyMove);
        infoDisplay.getChildren().add(fen);
        //infoDisplay.getChildren().add(repetition);
        infoDisplay.setPadding(new Insets(190, 100, 20, 50));
        infoDisplay.setSpacing(10);
        infoDisplay.setLayoutX(750);
        infoDisplay.setLayoutY(50);

        HBox backToMenu = new HBox();
        Button resetGame = new Button("Main Menu");
        backToMenu.getChildren().add(resetGame);
        infoDisplay.getChildren().add(backToMenu);

        HBox endGame = new HBox();
        Button quit = new Button("forfeit");
        endGame.getChildren().add(quit);
        infoDisplay.getChildren().add(endGame);

        GridPane board = new GridPane();
        board.setLayoutX(50);
        board.setLayoutY(50);
        board.setOnMouseReleased(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toX=(int)(event.getX());
                toY=(int)(event.getY());
                toX=toX/80;
                toY=toY/80;
                if (toX==8 || toY==8) {
                    toX=7;
                    toY=7;
                }
                Board b=game.getBoard();
                Cell[][]cells = b.getCells();
                Player player = game.getCurrentTurn();
                Cell dest = cells[toX][7-toY];
                Cell source =player.getSource();
                player.setDest(dest);
                String pieceType="";
                if(source.getPiece().getType()== PieceType.PAWN && ((Pawn)(source.getPiece())).checkPawnPromotion(source) && game.getBlackPlayer() instanceof Human && game.getWhitePlayer() instanceof Human) {
                    Alert selection= new Alert(Alert.AlertType.NONE);
                    selection.setTitle("Pawn Promotion");
                    selection.setContentText("Select one of the below to promote:");
                    ButtonType queenButton = new ButtonType("Queen");
                    ButtonType knightButton = new ButtonType("Knight");
                    ButtonType bishopButton = new ButtonType("Bishop");
                    ButtonType rookButton = new ButtonType("Rook");
                    selection.getButtonTypes().setAll(queenButton, knightButton, bishopButton, rookButton);
                    Optional<ButtonType> result = selection.showAndWait();
                    if (result.get() == queenButton) {
                        pieceType="QUEEN";
                    } else if (result.get() == knightButton) {
                        pieceType="KNIGHT";
                    } else if(result.get() == bishopButton){
                        pieceType="BISHOP";
                    }
                    else{
                        pieceType="ROOK";
                    }
                    //selection.show();
                }
                if (player.getColor()== chessgame.Color.WHITE) {
                        boolean success=playerWhite.move(game, pieceType);
                    update(board, root);
                        if (playerBlack.getType() != PlayerType.Human)
                           if (success && game.getGameStatus() == CONTINUE) {
                                playerBlack.move(game, pieceType);
                                update(board, root);
                            }
                } else {
                    System.out.println("y");
                    boolean success=playerBlack.move(game,pieceType);
                    update(board, root);
                        if (playerWhite.getType() != PlayerType.Human)
                            if (success && game.getGameStatus() == CONTINUE) {
                                playerWhite.move(game,pieceType);
                                update(board, root);
                            }
                }
            }
        });

        /**
         * Creates the squares for displaying valid moves for the selected pieces
         */
        board.setOnMousePressed(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fromX=(int)(event.getX());
                fromY=(int)(event.getY());
                fromX=fromX/80;
                fromY=fromY/80;
                Rectangle selected = new Rectangle(1,1,77,77);
                if (fromX==8 || fromY==8) {
                    fromX=7;
                    fromY=7;
                }

              //  System.out.println(fromX);
              //  System.out.println(fromY);
                selected.setFill(Color.TRANSPARENT);
                selected.setStrokeWidth(2.8);
                selected.setStroke(Color.BLACK);
                board.add(selected,fromX,fromY);

                Board b=game.getBoard();
                Cell[][]cells = b.getCells();
                Player player = game.getCurrentTurn();
                Cell source = cells[fromX][7-fromY];
                if (player.setSource(source, game)) {
                    Piece selectedPiece= source.getPiece();
                    ArrayList<Cell>possibleMoves=selectedPiece.legalMoves(cells);
                    List<Pair<Cell, Cell>> allmoves = new ArrayList<Pair<Cell, Cell>>();
                    for (Cell cell : possibleMoves) {
                        Pair<Cell, Cell> move = new Pair<Cell, Cell>(source, cell);
                        allmoves.add(move);
                    }
                    List<Pair<Cell, Cell>> filteredMoves = GameUtils.allFilteredMoves(game, allmoves);
                    for(int i=0;i<filteredMoves.size();i++) {
                        Rectangle validSq = new Rectangle(1,1,77,77);
                        validSq.setFill(Color.GREEN);
                        validSq.setStrokeWidth(2.8);
                        validSq.setStroke(Color.BLACK);
                        int x= filteredMoves.get(i).getValue().getPosition().getXCoordinate();
                        int y= filteredMoves.get(i).getValue().getPosition().getYCoordinate();
                        board.add(validSq,x ,7-y);
                    }
                }

            }
        });


        baseBoard(board);
        drawBoard(board);
        drawEdges(root);
        root.getChildren().add(infoDisplay);
        root.getChildren().add(board);
        int h = 750;
        int w = (int) (h * 16 / 9);
        mainScene = new Scene(root, w, h);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(drawStartingPage(primaryStage, startScene));
        primaryStage.show();
        //update(board, root);
        resetGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = new Game(playerWhite,playerBlack);
                update(board, root);
                primaryStage.setScene(drawStartingPage(primaryStage, startScene));
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Player curr= game.getCurrentTurn();
                chessgame.Color currColor= curr.getColor();
                if(currColor== chessgame.Color.WHITE) {
                    game.setGameStatus(BLACKWINS);
                }
                else{
                    game.setGameStatus(WHITEWINS);
                }
                update(board,root);
                game = new Game(playerWhite,playerBlack);
                update(board, root);
            }
        });
    }



    /**
     * Draws the numbers and letters around the edge of the board
     */
    public void drawEdges(Pane root) {
        int hspace = 67;
        VBox leftNumberEdge = new VBox();
        VBox rightNumberEdge = new VBox();
        HBox topLetterEdge = new HBox();
        HBox bottomLetterEdge = new HBox();

        for (int i = 0; i < 8; i++) {
            char ttemp = (char) ('a' + i);
            String tfile = "" + ttemp;
            Text tfileText = new Text();
            tfileText.setText(tfile);
            tfileText.setFont(Font.font("Verdana", 20));
            topLetterEdge.getChildren().add(tfileText);
        }
        topLetterEdge.setPrefSize(600, 30);
        topLetterEdge.setLayoutX(88);
        topLetterEdge.setLayoutY(20);
        topLetterEdge.setSpacing(hspace);
        root.getChildren().add(topLetterEdge);

        for (int i = 0; i < 8; i++) {
            char btemp = (char) ('a' + i);
            String bfile = "" + btemp;
            Text bfileText = new Text();
            bfileText.setText(bfile);
            bfileText.setFont(Font.font("Verdana", 20));
            bottomLetterEdge.getChildren().add(bfileText);
        }
        bottomLetterEdge.setPrefSize(560, 30);
        bottomLetterEdge.setLayoutX(88);
        bottomLetterEdge.setLayoutY(690);
        bottomLetterEdge.setSpacing(hspace);
        root.getChildren().add(bottomLetterEdge);

        for (int i = 8; i > 0; i--) {
            int ntemp = (i);
            String number = "" + ntemp;
            Text tempNumber = new Text();
            tempNumber.setText(number);
            tempNumber.setFont(Font.font("Verdana", 20));
            leftNumberEdge.getChildren().add(tempNumber);
        }
        leftNumberEdge.setPrefSize(560, 30);
        leftNumberEdge.setLayoutX(15);
        leftNumberEdge.setLayoutY(85);
        leftNumberEdge.setSpacing(53);
        root.getChildren().add(leftNumberEdge);

        for (int i = 8; i > 0; i--) {
            int ntemp = (i);
            String number = "" + ntemp;
            Text tempNumber = new Text();
            tempNumber.setText(number);
            tempNumber.setFont(Font.font("Verdana", 20));
            rightNumberEdge.getChildren().add(tempNumber);
        }
        rightNumberEdge.setPrefSize(560, 30);
        rightNumberEdge.setLayoutX(705);
        rightNumberEdge.setLayoutY(85);
        rightNumberEdge.setSpacing(53);
        root.getChildren().add(rightNumberEdge);
    }

    /**
     * Draws the squares for the board in the display
     */
    public void baseBoard(GridPane board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int check_1 = i + j;
                Rectangle square_s = new Rectangle(0, 0, 80, 80);
                if (check_1 % 2 == 0) {
                    square_s.setFill(Color.WHITE);
                } else {
                    square_s.setFill(Color.SILVER);
                }
                board.add(square_s, i, j, 1, 1);
            }
        }
    }

    /**
     * Adds the com.example.chess.pictures of the pieces to the board
     */
    public void drawBoard(GridPane b) {
        //GameInfo.printState(game);
        Board board=game.getBoard();
        Cell[][]cells = board.getCells();

        for (int i = 7; i >= 0; i--) {
            for (int j = 7; j >= 0; j--) {
                Piece currentPiece=cells[i][j].getPiece();
                if(currentPiece==null){continue;}
                chessgame.Color pieceColor = currentPiece.getColor();
                String base = "file:"+System.getProperty("user.dir")+"/src/main/java/gui/pictures/";
                //System.out.println("Working Directory = " + System.getProperty("user.dir"));
                //System.out.println("x");
                String picture;
                switch (currentPiece.getType()) {
                    case KING:
                        //System.out.println("x");
                        picture = (pieceColor== chessgame.Color.WHITE) ? "WhiteKing.png" : "BlackKing.png";
                        //System.out.println(base+picture);
                        break;
                    case QUEEN:
                        picture = (pieceColor== chessgame.Color.WHITE) ? "WhiteQueen.png" : "BlackQueen.png";
                        break;
                    case ROOK:
                        picture = (pieceColor== chessgame.Color.WHITE) ? "WhiteRook.png" : "BlackRook.png";
                        break;
                    case BISHOP:
                        picture = (pieceColor== chessgame.Color.WHITE) ? "WhiteBishop.png" : "BlackBishop.png";
                        break;
                    case KNIGHT:
                        picture = (pieceColor== chessgame.Color.WHITE) ? "WhiteKnight.png" : "BlackKnight.png";
                        break;
                    case PAWN:
                        picture = (pieceColor== chessgame.Color.WHITE) ? "WhitePawn.png" : "BlackPawn.png";
                        break;
                    default:
                        //System.out.println("x");
                        picture = null;
                        break;
                }
                //System.out.println(picture);

                    Image pic = new Image(base + picture);
                    ImageView toPlace = new ImageView(pic);
                    toPlace.setPreserveRatio(true);
                    b.add(toPlace, i, 7-j, 1, 1);


            }
        }
    }

    /**
     * Updates the display of the board
     */
    public void update(GridPane board, Pane root) {
        baseBoard(board);
        whosTurn.setText("Currently "+ game.getCurrentTurn().toCharacter() + "'s turn.");
       /* turnNumber.setText("Turn: " + game.getTurn());
        fiftyMove.setText("Fifty-move Rule: " + game.getPeace());*/
        fen.setText("FEN: " + game.currentFEN());
        /*whiteTime.setText("White Time: " + (game.getClock().getWhiteTime() / 1000) + " seconds");
        blackTime.setText(("Black Time: " + (game.getClock().getBlackTime() / 1000) + " seconds"));
        repetition.setText("Repetition: " + GameHelper.repetition(game));
*/
        StackPane endDisplay = new StackPane();
        Rectangle background = new Rectangle(0, 0, 320, 80);
        background.setFill(Color.BLACK);
        Text winnerText = new Text();
        String win;
        switch (game.getGameStatus()){
            case CONTINUE:
                win = "Ongoing";
                break;
            case WHITEWINS:
                win = "White wins.";
                break;
            case BLACKWINS:
                win = "Black wins.";
                break;
            case DRAW:
                win = "Draw.";
                break;
            default:
                win = "Error";
                break;
        }
        winnerText.setText(win);
        winnerText.setFont(Font.font("verdana", 20));
        winnerText.setFill(Color.WHITE);
        endDisplay.getChildren().add(background);
        endDisplay.getChildren().add(winnerText);
        endDisplay.setLayoutX(750);
        endDisplay.setLayoutY(10);
        root.getChildren().add(endDisplay);

        drawBoard(board);
        if(game.getGameStatus()!= CONTINUE){

            Alert a = new Alert(Alert.AlertType.NONE);
            switch (game.getGameStatus()){
                case WHITEWINS:
                    a.setContentText("White wins.");
                    break;
                case BLACKWINS:
                    a.setContentText("Black wins.");
                    break;
                case DRAW:
                    a.setContentText("Draw.");
                    break;
                default:
                    break;
            }
            a.show();
            Window window = a.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e -> a.hide());
        }
        //pawn promotion
    }

    /**
     * Function to draw the main menu
     */
    public Scene drawStartingPage(Stage primaryStage, Scene s){
        Pane surface = new Pane();

        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(100,0,0,550));
        Text t = new Text("Chess");
        t.setTextAlignment(TextAlignment.CENTER);
        t.setFont(Font.font ("Verdana", 80));
        t.setFill(Color.BLACK);
        title.getChildren().add(t);


        VBox modes = new VBox();
        modes.setAlignment(Pos.CENTER);
        Text txt = new Text("Choose Game Mode");
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setFont(Font.font ("Verdana", 40));
        txt.setFill(Color.BLACK);
        modes.getChildren().add(txt);
        Button hVSh = new Button("Human vs Human");
        Button hVSm = new Button("Human vs AlphaBeta");
        Button mVSr = new Button("Human vs MinMax");
        modes.getChildren().add(hVSh);
        modes.getChildren().add(hVSm);
        modes.getChildren().add(mVSr);
        modes.setPadding(new Insets(300,0,0,490));
        modes.setSpacing(10);

        modes.setPrefWidth(350);
        hVSh.setMinWidth(modes.getPrefWidth());
        hVSh.setMinHeight((modes.getPrefWidth() / 9));
        hVSm.setMinWidth(modes.getPrefWidth());
        hVSm.setMinHeight((modes.getPrefWidth() / 9));
        mVSr.setMinWidth(modes.getPrefWidth());
        mVSr.setMinHeight((modes.getPrefWidth() / 9));

        hVSh.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                playerWhite = new Human(chessgame.Color.WHITE);
                playerBlack = new Human(chessgame.Color.BLACK);
                game = new Game(playerWhite,playerBlack);
                primaryStage.setScene(mainScene);
            }
        });
        hVSm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                playerWhite = new Human(chessgame.Color.WHITE);
                playerBlack = new AlphaBeta(chessgame.Color.BLACK);
                game = new Game(playerWhite,playerBlack);
                primaryStage.setScene(mainScene);
            }
        });
        mVSr.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                playerWhite = new Human(chessgame.Color.WHITE);
                playerBlack = new Minimax(chessgame.Color.BLACK);
                game = new Game(playerWhite,playerBlack);
                primaryStage.setScene(mainScene);
            }
        });

        surface.getChildren().add(title);
        surface.getChildren().add(modes);
        int h = 750;
        int w = (int) (h * 16 / 9);
        s = new Scene(surface, w, h);
        return s;
    }
}