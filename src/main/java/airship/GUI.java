package airship;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Extra class to enable deployment to jars.
 */
public class GUI extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    new MainCtrl(stage);
  }
}
