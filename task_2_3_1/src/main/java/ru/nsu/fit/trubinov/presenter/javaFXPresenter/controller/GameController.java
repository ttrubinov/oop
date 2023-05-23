package ru.nsu.fit.trubinov.presenter.javaFXPresenter.controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.fit.trubinov.presenter.javaFXPresenter.GamePresenter;
import ru.nsu.fit.trubinov.view.javaFXViewer.JavaFXViewer;

import java.io.IOException;

public class GameController {
    public Canvas canvas;
    public AnchorPane pane;
    public Text text;
    public Canvas canvas2;
    private Stage stage;
    private int difficultyLevel = 8;
    private int gameSpeed = 1000;
    private JavaFXViewer viewer;
    private GamePresenter game;

    public void setDifficultyLevelAndGameSpeed(int difficultyLevel, int gameSpeed) {
        this.difficultyLevel = difficultyLevel;
        this.gameSpeed = gameSpeed;
    }

    public void init(Stage stage) {
        this.stage = stage;
        viewer = new JavaFXViewer(canvas);
        game = new GamePresenter(viewer, (int) canvas.getWidth(),
                (int) canvas.getHeight(), difficultyLevel, gameSpeed, true);
        game.setOnTimelineFinishListener(() -> {
            try {
                gameEnd();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> game.onKeyPressed(event));
        game.start();
        setResizeListener();
        resize();
        game.setScoreUpdater(score -> text.setText("Score: " + score));
    }


    private void setResizeListener() {
        canvas.widthProperty().bind(stage.widthProperty().divide(1.3));
        canvas.heightProperty().bind(stage.heightProperty());
        canvas2.widthProperty().bind(stage.widthProperty().subtract(stage.widthProperty().divide(1.3)));
        canvas2.heightProperty().bind(stage.heightProperty());
        text.xProperty().bind(canvas.widthProperty().add(10));
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> resize();
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    private void resize() {
        viewer.drawBedrockBorders(canvas2);
        double newWidth = stage.getWidth() / 1.3;
        double newHeight = stage.getHeight();
        canvas2.setLayoutX(newWidth);
        game.resize((int) newWidth, (int) newHeight);
    }

    public void gameEnd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MenuController menuController = loader.getController();
        menuController.initGame();
        menuController.init(stage);
        stage.setScene(scene);
        stage.show();
    }
}
