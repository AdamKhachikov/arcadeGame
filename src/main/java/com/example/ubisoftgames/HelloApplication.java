package com.example.ubisoftgames;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    enum UserAction {
        LEFT, RIGHT, NONE;
    }

    int APP_W = 800;
    int APP_H = 600;
    final int RADIUS = 10;
    final int RECT_W = 100;
    final int RECT_H = 20;
    boolean ballLeft = true;
    boolean ballUp = true;
    Timeline timeline = new Timeline();

    Circle ball = new Circle(RADIUS);
    Rectangle rectangle = new Rectangle(RECT_W, RECT_H);
    UserAction action = UserAction.LEFT;
    boolean running = true;
    double speed = 0.1;
    int count = 0;
    Label label = new Label();


    Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        rectangle.setTranslateX(APP_W / 2 - RECT_W / 2);
        rectangle.setTranslateY(APP_H - RECT_H);
        rectangle.setFill(Color.BLUE);
        ball.setTranslateX(APP_W / 2);
        ball.setTranslateY(APP_H / 2);
        ball.setFill(Color.BLACK);
        KeyFrame frame = new KeyFrame(Duration.seconds(speed),
                actionEvent -> {
                    APP_W = (int) root.getWidth();
                    APP_H = (int) root.getHeight();
                    if (!running)
                        return;
                    label.setText(" " + count);
                    switch (action) {
                        case LEFT -> {
                            double rectX = rectangle.getTranslateX();
                            if (rectX - 5 >= 0) {
                                rectangle.setTranslateX(rectX - 5);
                            }
                        }
                        case RIGHT -> {
                            double rectX = rectangle.getTranslateX();
                            if (rectX + RECT_W + 5 <= APP_W) {
                                rectangle.setTranslateX(rectX + 5);
                            }
                        }
                    }
                    ball.setTranslateX(ball.getTranslateX() + (ballLeft ? -5:5));
                    ball.setTranslateY(ball.getTranslateY() + (ballUp ? -5:5));
                    double ballX = ball.getTranslateX();
                    double ballY = ball.getTranslateY();
                    if (ballX - RADIUS <= 0) {
                        ballLeft = false;
                        count++;
                    }
                    else if (ballX + RADIUS >= APP_W) {
                        ballLeft = true;
                        count++;
                    }
                    else if (ballY - RADIUS <= 0) {
                        ballUp = false;
                        count++;
                    }
                    else if (ballX >= rectangle.getTranslateX()
                            && ballX <= rectangle.getTranslateX() + RECT_W
                            && ballY + RADIUS >= rectangle.getTranslateY()){
                            ballUp = true;
                    } else if (ballY >= APP_H)
                        restartGame();
        });
                    timeline.getKeyFrames().add(frame);
                    timeline.setCycleCount(Timeline.INDEFINITE);
        root.getChildren().addAll(rectangle, ball, label);
        return root;
    }
    public void restartGame(){
        stopGame();
        startGame();
    }
    public void stopGame(){
        running = false;
        timeline.stop();
        count = 0;
    }
    public void startGame(){
        rectangle.setTranslateX(APP_W / 2 - RECT_W / 2);
        rectangle.setTranslateY(APP_H - RECT_H);
        rectangle.setFill(Color.BLUE);
        ball.setTranslateX(APP_W / 2);
        ball.setTranslateY(APP_H / 2);
        ball.setFill(Color.BLACK);
        label.setTranslateX(50);
        label.setTranslateY(50);
        label.setFont(new Font(18));
        running = true;
        timeline.play();

    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> action = UserAction.LEFT;
                case D -> action = UserAction.RIGHT;
            }
        });
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        startGame();
    }

    public static void main(String[] args) {
        launch();
    }
}