module ru.nsu.fit.trubinov {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.googlecode.lanterna;

    exports ru.nsu.fit.trubinov.presenter.javaFXPresenter;
    opens ru.nsu.fit.trubinov.view.javaFXViewer to javafx.fxml;
    opens ru.nsu.fit.trubinov.presenter to javafx.fxml;
    opens ru.nsu.fit.trubinov.presenter.javaFXPresenter to javafx.fxml;
    opens ru.nsu.fit.trubinov.view.consoleViewer to javafx.fxml;
}
