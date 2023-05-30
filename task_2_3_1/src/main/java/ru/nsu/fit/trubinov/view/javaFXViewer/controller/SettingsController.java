package ru.nsu.fit.trubinov.view.javaFXViewer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.nsu.fit.trubinov.presenter.javaFXPresenter.GamePresenter;

import java.io.IOException;

public class SettingsController {

    private final int maxSpeed = 11;
    public Slider level;
    public Slider speed;
    public Button menu;
    public Canvas canvas;
    public AnchorPane pane;
    public Label speedLabel;
    public Label levelLabel;
    private Stage stage;
    private GamePresenter backgroundGame;
    private int difficultyLevel;
    private int gameSpeed = 300;

    public SettingsController() {
        canvas = new Canvas(768, 512);
        canvas.setOpacity(0.8);
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setBackgroundGame(GamePresenter backgroundGame) {
        this.backgroundGame = backgroundGame;
    }

    public void setDifficultyLevelAndGameSpeed(int difficultyLevel, int gameSpeed) {
        this.difficultyLevel = difficultyLevel;
        this.gameSpeed = gameSpeed;
    }

    public void init(Stage stage) {
        pane.getChildren().add(0, canvas);
        this.stage = stage;
        speed.setValue(maxSpeed - gameSpeed / 100);
        level.setValue(difficultyLevel);
        speed.valueProperty().addListener((observable, oldValue, newValue) -> gameSpeed = (maxSpeed - newValue.intValue()) * 100);
        level.valueProperty().addListener((observable, oldValue, newValue) -> difficultyLevel = newValue.intValue());
        setResizeListener();
        resize();
    }

    private void setResizeListener() {
        canvas.widthProperty().bind(stage.widthProperty());
        canvas.heightProperty().bind(stage.heightProperty());
        stage.widthProperty().addListener((observable, oldValue, newValue) -> resize());
        stage.heightProperty().addListener((observable, oldValue, newValue) -> resize());
    }

    private void resize() {
        level.setPrefWidth(stage.getWidth() / 2);
        speed.setPrefWidth(stage.getWidth() / 2);
        level.setPrefHeight(stage.getHeight() / 10);
        speed.setPrefHeight(stage.getHeight() / 10);
        level.setLayoutX(stage.widthProperty().divide(2).subtract(level.getWidth() / 2).intValue());
        speed.setLayoutX(stage.widthProperty().divide(2).subtract(speed.getWidth() / 2).intValue());
        level.setLayoutY(7 * stage.getHeight() / 15);
        speed.setLayoutY(9 * stage.getHeight() / 15);
        menu.setPrefWidth(stage.getWidth() / 5);
        menu.setPrefHeight(stage.getHeight() / 7);
        menu.setLayoutX(stage.widthProperty().divide(2).subtract(menu.getWidth() / 2).intValue());
        menu.setLayoutY(2 * stage.getHeight() / 10);
        levelLabel.setLayoutX(level.getLayoutX());
        levelLabel.setLayoutY(level.getLayoutY() - levelLabel.getHeight() + 10);
        speedLabel.setLayoutX(speed.getLayoutX());
        speedLabel.setLayoutY(speed.getLayoutY() - speedLabel.getHeight() + 10);
    }

    public void menuButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        MenuController menuController = loader.getController();
        menuController.setCanvas(canvas);
        menuController.setBackgroundGame(backgroundGame);
        menuController.setDifficultyLevelAndGameSpeed(difficultyLevel, gameSpeed);
        menuController.init(stage);
        stage.setScene(scene);
        stage.show();
    }
}
