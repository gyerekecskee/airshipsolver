package airshipg;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new MainCtrl(stage);
    }
}
