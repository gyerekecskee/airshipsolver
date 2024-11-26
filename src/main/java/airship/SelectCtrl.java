package airship;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller, responsible for handling the selection process.
 */
public class SelectCtrl implements Initializable {

  private final TileContainer tileContainer = TileContainer.getInstance();
  private TileType tileType;
  @FXML
  public HBox tipesbox;
  @FXML
  public MenuButton mButton;

  /**
   * Finishes the selection process.
   */
  public void done() {
    try {
      tileContainer.setTile(new Tile(tileType, 0));
      var stage = (Stage) mButton.getScene().getWindow();
      stage.close();
    } catch (NumberFormatException e) {
      var alert = new Alert(Alert.AlertType.ERROR, "Make sure you have selected an orientation");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.setResizable(false);
      alert.showAndWait();
    }
  }

  public void menuSelected(ActionEvent event) {
    MenuItem selected = (MenuItem) event.getSource();
    mButton.setText(selected.getText());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setCtrls(MainCtrl mainCtrl) {
  }

  public void choseAirship() {
    tileType = (TileType.AIRSHIP);
    done();
  }

  public void choseWhiteCorner() {
    tileType = (TileType.SMALL_CORNER);
    done();
  }

  public void choseLargeCorner() {
    tileType = (TileType.LARGE_CORNER);
    done();
  }

  public void choseEmpty() {
    tileType = (TileType.EMPTY);
    done();
  }

  public void choseLargeBottom() {
    tileType = (TileType.LARGE_BOTTOM);
    done();
  }

  public void choseDiagonal() {
    tileType = (TileType.DIAGONAL);
    done();
  }

  public void choseColumn() {
    tileType = (TileType.COLUMN);
    done();
  }
}