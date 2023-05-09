package ru.nsu.fit.trubinov.presenter.javaFXPresenter;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationStart extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        JavaFXPresenter presenter = new JavaFXPresenter(stage);
        presenter.start();
    }
}
