package ru.nsu.fit.trubinov.view.javaFXViewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.nsu.fit.trubinov.view.javaFXViewer.controller.MenuController;

import java.io.IOException;
import java.util.Objects;

/**
 * Starting the JavaFX game.
 */
public class ApplicationStart extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setMinWidth(768);
        stage.setMinHeight(512);
        stage.setTitle("Best Snake game");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        stage.setScene(scene);
        MenuController menuController = loader.getController();
        menuController.initGame();
        menuController.init(stage);
        stage.show();
    }
}
