package com.example.snakeladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {

    public static final int tileSize = 40, width = 10, height = 10,
       buttonLine = height*tileSize +50, infoLine=height*tileSize +20;

    Player playerFirst, playerSecond;
    boolean firstPlayerTurn = true, gameStart = false;
    int diceValue;
    private Pane createContent(){
        Pane root = new Pane();  // blank canvas is root
        root.setPrefSize(width*tileSize, height*tileSize+100);
        //putting 100 tiles
        for (int i=0;i<height;i++){
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(i*tileSize);  //translate X is that many pixels away from origin
                tile.setTranslateY(j*tileSize);
                root.getChildren().addAll(tile);
                /* this method is responsible for adding anything that you want to show on that root.
                If not present then screen will be blank.
                 */
            }
        }

        //putting image
        Image img = new Image("C:\\Users\\simra\\IdeaProjects\\SnakeLadder\\src\\img.png"); //loading the image
        ImageView boarImage = new ImageView(); // creating the object of image view to show the image on the UI
        boarImage.setFitWidth(width*tileSize); // setting dimensions
        boarImage.setFitHeight(height*tileSize);
        boarImage.setImage(img); //attaching the actual image

        root.getChildren().addAll(boarImage);

        //buttons and info
        Button startButton = new Button("START");
        startButton.setTranslateX(180);
        startButton.setTranslateY(buttonLine);

        Button playerOneButton = new Button("Player One");
        playerOneButton.setTranslateX(10);
        playerOneButton.setTranslateY(buttonLine);

        Button playerTwoButton = new Button("Player Two");
        playerTwoButton.setTranslateX(315);////300
        playerTwoButton.setTranslateY(buttonLine);

        Label diceLabel = new Label("Start the Game");
        diceLabel.setTranslateX(165);
        diceLabel.setTranslateY(infoLine);

        playerFirst = new Player("Amit", Color.BLACK, tileSize/2);
        playerSecond = new Player("Sumit", Color.WHITE, tileSize/2-5);

        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart) {
                    if (firstPlayerTurn) {
                        diceValue = rollDice();
                        diceLabel.setText(" Dice :  " + diceValue);
                        playerFirst.movePlayer(diceValue);
                        firstPlayerTurn = !firstPlayerTurn; //avoiding two turns
                        if(playerFirst.checkWinner()){
                            diceLabel.setText("Winner: " + playerFirst.getName());
                            startButton.setText("Restart");
                            startButton.setDisable(false);
                            firstPlayerTurn=true;
                            gameStart=false;
                        }
                    }
                }
            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart) {
                    if (!firstPlayerTurn) {
                        diceValue = rollDice();
                        diceLabel.setText(" Dice :  " + diceValue);
                        playerSecond.movePlayer(diceValue);
                        firstPlayerTurn = !firstPlayerTurn;
                        if(playerSecond.checkWinner()){
                            diceLabel.setText("Winner: " + playerSecond.getName());
                            startButton.setText("Restart");
                            startButton.setDisable(false);
                            firstPlayerTurn=true;
                            gameStart=false;
                        }
                    }
                }
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStart=true;
                startButton.setDisable(true);
                playerFirst.setStart();
                playerSecond.setStart();
            }
        });

        root.getChildren().addAll(startButton, playerOneButton, playerTwoButton, diceLabel,
             playerFirst.getCoin(), playerSecond.getCoin());

        return root;
    }

    private int rollDice(){
        return (int) (Math.random()*6 + 1);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake And Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}