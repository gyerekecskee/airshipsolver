package airship;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

/**
 * Controller, responsible for handling the selection scene.
 */
public class HelloCtrl implements Initializable {
  private MainCtrl mainCtrl;
  private TileContainer tileContainer = TileContainer.getInstance();
  private BoardContainer boardContainer = BoardContainer.getInstance();
  private Tile x0y0;
  private Tile x1y0;
  private Tile x2y0;
  private Tile x0y1;
  private Tile x1y1;
  private Tile x2y1;
  private Tile x0y2;
  private Tile x1y2;
  private Tile x2y2;
  @FXML
  public Button b11;
  @FXML
  public Button b12;
  @FXML
  public Button b13;
  @FXML
  public Button b21;
  @FXML
  public Button b22;
  @FXML
  public Button b23;
  @FXML
  public Button b31;
  @FXML
  public Button b32;
  @FXML
  public Button b33;
  @FXML
  public GridPane gPane;

  public void setCtrls(MainCtrl mainCtrl) {

    this.mainCtrl = mainCtrl;
  }

  public HelloCtrl() {

  }

  /**
   * Sets the tile of a specifies button.
   *
   * @param button th button which we want to set
   * @return the set tile
   */
  public Tile setGeneric(Button button) {
    try {
      mainCtrl.showSelect();
      Tile tile = tileContainer.getTile();
      String  imageName =  tile.getType().getImageName();
      URL url = this.getClass().getResource(imageName);
      button.setGraphic(new ImageView(new Image(url.toString())));
      return tile;
    } catch (NullPointerException e) {
      throw new RuntimeException("No image found for a piece");
    }
  }

  public void set31() {
    x2y0 = setGeneric(b31);
  }

  public void set32() {
    x2y1 = setGeneric(b32);
  }


  public void set33() {
    x2y2 = setGeneric(b33);
  }

  public void set21() {
    x1y0 = setGeneric(b21);
  }

  public void set22() {
    x1y1 = setGeneric(b22);
  }


  public void set23() {
    x1y2 = setGeneric(b23);
  }

  public void set11() {
    x0y0 = setGeneric(b11);

  }

  public void set12() {
    x0y1 = setGeneric(b12);

  }


  public void set13() {
    x0y2 = setGeneric(b13);

  }

  /**
   * Finishes the selection procedure.
   */
  public void done() {
    try {
      Board start = new Board(x0y2, x1y2, x2y2,
              x0y1, x1y1, x2y1,
              x0y0, x1y0, x2y0);
      boardContainer.setBoard(start);
      mainCtrl.showFinish();
    } catch (NoSuchElementException e) {
      var alert = new Alert(Alert.AlertType.ERROR, "Make sure there is an empty tile on the board");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.setResizable(false);
      alert.showAndWait();
    }
  }

  /** Rotates a tile.
   *
   * @param event the mouseevent
   * @param button the button which was clicked
   * @param tile the tile of the button
   */
  public void handleMouseClick(MouseEvent event, Button button, Tile tile) {
    if (event.getButton() == MouseButton.SECONDARY) { // Check if right mouse button is clicked
      ImageView imageView = (ImageView) button.getGraphic();
      imageView.setRotate(imageView.getRotate() - 90);
      tile.rotate(90);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    b11.setOnMouseClicked(click -> handleMouseClick(click, b11, x0y0));
    b12.setOnMouseClicked(click -> handleMouseClick(click, b12, x0y1));
    b13.setOnMouseClicked(click -> handleMouseClick(click, b13, x0y2));
    b21.setOnMouseClicked(click -> handleMouseClick(click, b21, x1y0));
    b22.setOnMouseClicked(click -> handleMouseClick(click, b22, x1y1));
    b23.setOnMouseClicked(click -> handleMouseClick(click, b23, x1y2));
    b31.setOnMouseClicked(click -> handleMouseClick(click, b31, x2y0));
    b32.setOnMouseClicked(click -> handleMouseClick(click, b32, x2y1));
    b33.setOnMouseClicked(click -> handleMouseClick(click, b33, x2y2));
  }
}