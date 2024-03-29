module ru.nsu.fit.trubinov {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.googlecode.lanterna;

    exports ru.nsu.fit.trubinov.view.javaFXViewer.controller;
    exports ru.nsu.fit.trubinov.presenter.javaFXPresenter;
    exports ru.nsu.fit.trubinov.utils;
    opens ru.nsu.fit.trubinov.presenter.javaFXPresenter to javafx.fxml;
    opens ru.nsu.fit.trubinov.view.javaFXViewer.controller to javafx.fxml;
    opens ru.nsu.fit.trubinov.view.javaFXViewer to javafx.fxml;
    opens ru.nsu.fit.trubinov.presenter to javafx.fxml;
    opens ru.nsu.fit.trubinov.view.consoleViewer to javafx.fxml;
    exports ru.nsu.fit.trubinov.view.javaFXViewer;
}
