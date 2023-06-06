package ru.nsu.fit.trubinov.view.javaFXViewer.controller;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.fit.trubinov.presenter.javaFXPresenter.GamePresenter;
import ru.nsu.fit.trubinov.view.javaFXViewer.JavaFXViewer;

import java.io.IOException;

public class MenuController {
    public Canvas canvas;
    public Button settingsButton;
    public Button playButton;
    public AnchorPane pane;
    public Text text;
    private int difficultyLevel = 2;
    private int gameSpeed = 600;
    private JavaFXViewer viewer;
    private GamePresenter backgroundGame;
    private Stage stage;

    public MenuController() {
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
        setResizeListener();
        resize();
    }

    public void initGame() {
        viewer = new JavaFXViewer(canvas);
        final int backgroundGameDifficultyLevel = 1;
        final int backgroundGameSpeed = 100;
        backgroundGame = new GamePresenter(viewer, (int) canvas.getWidth(),
                (int) canvas.getHeight(), backgroundGameDifficultyLevel, backgroundGameSpeed, false);
        backgroundGame.start(); // Starting game in background of menu screen without user snake
    }

    private void setResizeListener() {
        canvas.widthProperty().bind(stage.widthProperty());
        canvas.heightProperty().bind(stage.heightProperty());
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> resize();
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
        text.setX(10);
        text.setY(20);
        text.wrappingWidthProperty().bind(stage.widthProperty());
    }

    private void resize() {
        double newWidth = stage.getWidth();
        double newHeight = stage.getHeight();
        double gap = newHeight / 1000;
        double newButtonWidth = newWidth / 10;
        double newButtonHeight = newHeight / 10;
        resizeButtons(gap, newWidth, newHeight, newButtonWidth, newButtonHeight);
        if (viewer != null) {
            viewer.setGraphicsContext(canvas.getGraphicsContext2D());
        }
        if (backgroundGame != null) {
            backgroundGame.resize((int) newWidth, (int) newHeight);
        }
    }

    private void resizeButtons(double gap, double newWidth, double newHeight,
                               double newButtonWidth, double newButtonHeight) {
        settingsButton.setLayoutX(newWidth / 2 - newButtonWidth / 2);
        settingsButton.setLayoutY(newHeight / 2 + gap * 10);
        playButton.setLayoutX(newWidth / 2 - newButtonWidth / 2);
        playButton.setLayoutY(newHeight / 2 - newButtonHeight - gap / 2);
        settingsButton.setMinWidth(newButtonWidth);
        settingsButton.setMinHeight(newButtonHeight);
        playButton.setMinWidth(newButtonWidth);
        playButton.setMinHeight(newButtonHeight);
    }

    public void settingButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        SettingsController settingsController = loader.getController();
        settingsController.setCanvas(canvas);
        settingsController.setBackgroundGame(backgroundGame);
        settingsController.setDifficultyLevelAndGameSpeed(difficultyLevel, gameSpeed);
        settingsController.init(stage);
        stage.setScene(scene);
        stage.show();
    }

    public void playButton(ActionEvent actionEvent) throws IOException {
        backgroundGame.timeline.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        GameController gameController = loader.getController();
        gameController.setDifficultyLevelAndGameSpeed(difficultyLevel, gameSpeed);
        gameController.init(stage);
        stage.setScene(scene);
        stage.show();
    }
}
