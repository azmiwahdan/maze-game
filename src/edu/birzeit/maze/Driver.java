package edu.birzeit.maze;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class Driver extends Application {
    private static ArrayList<String> paths = new ArrayList<>();
    private static int[][] mazeArr;


    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage firstStage) throws Exception {
        // first stage,
        firstStage.getIcons().add(new Image("file:icon.jpg"));
        Label welcome = new Label("Welcome to My Maze");
        welcome.setAlignment(Pos.TOP_CENTER);
        welcome.setFont(new Font("impact", 23));
        welcome.setTextFill(Color.AZURE);

        Button continu = new Button("Continue");
        continu.setFont(new Font("Algerian", 16));
        continu.setTextFill(Color.GREEN);

        Button exit = new Button("Exit");
        exit.setOnAction(e -> {
            JOptionPane.showMessageDialog(null, "Good_luck_@azmiBzu");
            System.exit(0);
        });
        exit.setTextFill(Color.RED);
        exit.setFont(new Font("Algerian", 16));
        exit.setPrefWidth(98);

        HBox hBox1 = new HBox(20);
        hBox1.getChildren().addAll(continu, exit);
        hBox1.setAlignment(Pos.CENTER);


        VBox fisrtVbox = new VBox(20);
        fisrtVbox.getChildren().addAll(welcome, hBox1);

        fisrtVbox.setAlignment(Pos.CENTER);

        Image im = new Image("file:firstBackGround.jpg");

        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(im, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        // create Background
        Background background = new Background(backgroundimage);
        // set background
        fisrtVbox.setBackground(background);
        fisrtVbox.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Scene firstScene = new Scene(fisrtVbox, 400, 400);
        firstStage.setScene(firstScene);
        firstStage.setTitle("Maze");
        firstStage.show();


        // third stage,go to maze ...
        Stage mazeStage = new Stage();
        mazeStage.getIcons().add(new Image("file:icon.jpg"));

        Button source=new Button("Source");
        TextField t1=new TextField();
        TextField t2=new TextField();


        Button check = new Button("Check");
        check.setTextFill(GREEN);
        check.setFont(new Font("Copperplate Gothic Bold", 13));
        check.setOnAction(e -> {
            String res = "";
            if (Maze.collectionPathsIndexes(0, 0, mazeArr).size() == 0) {
                res = "No Solution!";
            } else {
                res = "Yes,we have solution";
            }
            JOptionPane.showMessageDialog(null, res);
        });

        Button count = new Button("Number of Paths");
        count.setOnAction(e -> {
            paths.clear();
            paths = Maze.collectionPathsIndexes(0, 0, mazeArr);
            JOptionPane.showMessageDialog(null, "number of paths = " + paths.size());


        });
        count.setTextFill(BLUE);
        count.setFont(new Font("Copperplate Gothic Bold", 13));

        Button bestPath = new Button("Best Path");
        bestPath.setTextFill(CRIMSON);
        bestPath.setFont(new Font("Copperplate Gothic Bold", 13));

        Button generate = new Button("Generate new Maze");
        generate.setTextFill(CRIMSON);
        generate.setFont(new Font("Copperplate Gothic Bold", 13));


        // ComboBox<Integer> comboBox = new ComboBox<>();
        autoGenerateMaze();


        Button reset = new Button("Reset");
        reset.setTextFill(DARKOLIVEGREEN);
        reset.setFont(new Font("Copperplate Gothic Bold", 13));

        VBox menue = new VBox(40);
        menue.setPadding(new Insets(20));


        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane = resetGridPane();
        HBox mazeBox = new HBox(10);
        mazeBox.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        Button back = new Button("Close");


        HBox allPaths = new HBox(5);
        Button l = new Button("Run");
        l.setFont(new Font("Copperplate Gothic Bold", 15));
        l.setTextFill(DARKORCHID);

        TextField textField = new TextField("");
        textField.setPrefWidth(40);
        allPaths.getChildren().addAll(l, textField);
        menue.getChildren().addAll(check, count, bestPath, allPaths, generate, reset, back);


        back.setAlignment(Pos.BOTTOM_LEFT);
        back.setTextFill(RED);
        back.setFont(new Font("Copperplate Gothic Bold", 13));
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(gridPane);

        mazeBox.setPadding(new Insets(19));
        mazeBox.setAlignment(Pos.CENTER);
        mazeBox.getChildren().addAll(vBox, menue);
        Image im2 = new Image("file:background.jpg");

        // create a background image
        BackgroundImage backgroundimage2 = new BackgroundImage(im2, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // create Background
        Background background2 = new Background(backgroundimage2);

        // set background
        mazeBox.setBackground(background2);
//        box.setBackground(background);
        Scene mazeScene = new Scene(mazeBox, 1000, 700);
        mazeStage.setScene(mazeScene);
        continu.setOnAction(e -> {
            mazeStage.show();
            firstStage.close();
        });

        generate.setOnAction(e -> {
            initializeMaze();
            //paths=Maze.collectionPathsIndexes(0,0,mazeArr);
            vBox.getChildren().remove(0);
            vBox.getChildren().add(resetGridPane());

        });

        //get best path
        bestPath.setOnAction(e -> {
            if (mazeArr == null || Maze.collectionPathsIndexes(0, 0, mazeArr).size() == 0) {
                JOptionPane.showMessageDialog(null, "No Solution!");


            } else {
                String path = Maze.getBestSolution(0, 0, mazeArr);

                GridPane pane = fillGridPane(path);
                vBox.getChildren().remove(0);
                vBox.getChildren().add(pane);
            }

        });

        // get all solutions


        reset.setOnAction(e -> {
            vBox.getChildren().remove(0);
            vBox.getChildren().add(resetGridPane());
        });

        back.setOnAction(e -> {
            vBox.getChildren().remove(0);
            vBox.getChildren().add(resetGridPane());
            mazeStage.close();
            firstStage.show();
        });

        l.setOnAction(e -> {
            if (textField.getText().length() == 0 || textField.getText() == null) {
                JOptionPane.showMessageDialog(null, "invalid ,enter path's number!");

            } else {
                int index = Integer.parseInt(textField.getText().trim());
                if (index <= 0 || index > Maze.collectionPathsIndexes(0, 0, mazeArr).size()) {
                    JOptionPane.showMessageDialog(null, "invalid input,try again!");
                } else if (Maze.collectionPathsIndexes(0, 0, mazeArr).size() == 0) {
                    JOptionPane.showMessageDialog(null, "invalid input,No Solution!!");

                } else {
                    vBox.getChildren().remove(0);
                    vBox.getChildren().add(0, fillGridPane(Maze.collectionPathsIndexes(0, 0, mazeArr).get(index - 1)));

                }
            }


        });
    }


    private static GridPane fillGridPane(String path) {

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        int count = 1;
        for (int i = 0; i < mazeArr.length; i++) {
            for (int j = 0; j < mazeArr.length; j++) {
                Button button = new Button();
                button.setBorder(new Border(
                        new BorderStroke(RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                button.setPrefSize(80, 80);
                button.setTextFill(WHEAT);
                if (mazeArr[j][i] == 0) {
                    button.setBackground(new Background(new BackgroundFill(GRAY, new CornerRadii(5), Insets.EMPTY)));
                } else if (i == 0 && j == 0) {
                    button.setBackground(new Background(new BackgroundFill(BLUE, new CornerRadii(5), Insets.EMPTY)));
                } else if (path.contains("(" + j + "," + i + ")->S")) {


                    button.setBackground(new Background(new BackgroundFill(BLUE, new CornerRadii(5), Insets.EMPTY)));
                    count++;
                    button.setText("S");

                } else if (path.contains("(" + j + "," + i + ")->W")) {

                    button.setBackground(new Background(new BackgroundFill(BLUE, new CornerRadii(5), Insets.EMPTY)));
                    count++;
                    button.setText("W");

                } else if (path.contains("(" + j + "," + i + ")->E")) {

                    button.setBackground(new Background(new BackgroundFill(BLUE, new CornerRadii(5), Insets.EMPTY)));
                    count++;
                    button.setText("E");

                } else if (path.contains("(" + j + "," + i + ")->UP")) {

                    button.setBackground(new Background(new BackgroundFill(BLUE, new CornerRadii(5), Insets.EMPTY)));
                    count++;
                    button.setText("UP");

                } else if (i == mazeArr.length - 1 && j == mazeArr.length - 1) {
                    button.setBackground(new Background(new BackgroundFill(WHITE, new CornerRadii(5), Insets.EMPTY)));

                } else {
                    button.setBackground(new Background(new BackgroundFill(WHITE, new CornerRadii(5), Insets.EMPTY)));
                }

                gridPane.add(button, i, j);

            }


        }
        gridPane.setBorder(new Border(
                new BorderStroke(GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        return gridPane;
    }

    private static GridPane resetGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if (mazeArr != null) {
            for (int i = 0; i < mazeArr.length; i++) {
                for (int j = 0; j < mazeArr.length; j++) {
                    Button b = new Button();
                    b.setBorder(new Border(
                            new BorderStroke(RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                    b.setPrefSize(80, 80);
                    if (mazeArr[j][i] == 0) {
                        b.setBackground(new Background(new BackgroundFill(GRAY, new CornerRadii(5), Insets.EMPTY)));
                        b.setTextFill(WHITE);
                        b.setFont(new Font("Kristen ITC", 14));

                    } else {
                        b.setBackground(new Background(new BackgroundFill(WHITE, new CornerRadii(5), Insets.EMPTY)));

                    }


                    gridPane.add(b, i, j);

                }

            }
            gridPane.setBorder(new Border(
                    new BorderStroke(SKYBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        return gridPane;
    }

    private static void autoGenerateMaze() {

        mazeArr = new int[][]{
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 1, 0},
                {0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1}


        };
    }

    private static void initializeMaze() {
        mazeArr = new int[8][8];
        for (int i = 0; i < mazeArr.length; i++)
            for (int j = 0; j < mazeArr.length; j++) {
                mazeArr[i][j] = (int) (Math.random() * 10);
            }

        for (int i = 0; i < mazeArr.length; i++) {
            mazeArr[0][i] = 0;
        }

        for (int i = 0; i < mazeArr.length; i++) {
            mazeArr[mazeArr.length - 1][i] = 0;
        }
        for (int i = 0; i < mazeArr.length; i++) {
            mazeArr[i][0] = 0;
        }
        for (int i = 0; i < mazeArr.length; i++) {
            mazeArr[i][mazeArr.length - 1] = 0;
        }
        for (int i = 2; i < mazeArr.length; i++) {
            mazeArr[i][1] = 0;
        }
        mazeArr[4][1] = 1;

        for (int i = 0; i < mazeArr.length; i++) {
            mazeArr[i][mazeArr.length - 2] = 0;
        }
        for (int i = 0; i < mazeArr.length; i++) {
            mazeArr[i][mazeArr.length - 2] = 0;
        }

        mazeArr[0][0] = 1;
        mazeArr[0][1] = 1;
        mazeArr[1][0] = 0;
        mazeArr[mazeArr.length - 1][mazeArr.length - 1] = 1;
        mazeArr[mazeArr.length - 1][mazeArr.length - 2] = 1;
        mazeArr[mazeArr.length - 2][mazeArr.length - 2] = 1;


    }


}
