package airshipg;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainCtrl {


  private static airshipg.BoardContainer boardContainer = airshipg.BoardContainer.getInstance();
  private Stage primaryStage;
  private airshipg.HelloCtrl helloCtrl;
  private Scene hello;
  private FinishCtrl finishCtrl;
  private Scene finish;
  private boolean debugging = false;


  public MainCtrl(Stage primaryStage) throws IOException {
    this.primaryStage = primaryStage;

    String imageUrl = String.valueOf(Main.class.getResource("board-empty.png")); // Replace with your image file path

    // Create a BackgroundImage
    BackgroundImage backgroundImage = new BackgroundImage(
        new Image(imageUrl), // ImagePattern for the image
        BackgroundRepeat.NO_REPEAT, // No repeat
        BackgroundRepeat.NO_REPEAT, // No repeat
        BackgroundPosition.CENTER, // Center the image
        new BackgroundSize(
            1.0,                // Width scaling factor
            1.0,                // Height scaling factor
            true,               // Width aspect ratio
            true,               // Height aspect ratio
            false,              // Size contain
            false               // Size cover
        ) // No size
    );

    // Create a Background using the BackgroundImage
    Background background = new Background(backgroundImage);



    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

    hello = new Scene(fxmlLoader.load());
    helloCtrl = fxmlLoader.getController();
    helloCtrl.setCtrls(this);
    // Apply the background to the Pane
//    helloCtrl.setBackground(background);

    fxmlLoader = new FXMLLoader(Main.class.getResource("finish.fxml"));
    Parent root = fxmlLoader.load();
//    root.setFocusTraversable(false);
    finish = new Scene(root);
    finishCtrl = fxmlLoader.getController();
//    finishCtrl.setBackground(background);

//    helloCtrl.setCtrls(this);


//    this.selectCtrl = select.getKey();
//    this.select = new Scene(select.getValue());
//
//    this.helloCtrl = hello.getKey();
//    this.hello = new Scene(hello.getValue());

    if (debugging) {
      boardContainer.setBoard(initializeStartingPosition2());
      showFinish();
    } else {
      showHello();

      primaryStage.setTitle("AirshipSolver");
    }
    primaryStage.show();
  }

  public void showHello() {
    primaryStage.setScene(hello);
  }

  public void showSelect() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("selecttile.fxml"));
      Scene select = new Scene(fxmlLoader.load());
      SelectCtrl selectCtrl = fxmlLoader.getController();
      selectCtrl.setCtrls(this);
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(select);
      stage.setResizable(false);
      stage.showAndWait();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void showFinish() {
    primaryStage.setScene(finish);
    finishCtrl.solve();
    finish.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.LEFT) {
        // Handle left arrow key press (e.g., navigate to the previous control)
        finishCtrl.toPrev(); // Simulate a button click
      } else if (event.getCode() == KeyCode.RIGHT) {
        // Handle right arrow key press (e.g., navigate to the next control)
        finishCtrl.toNext(); // Simulate a button click
      }
    });

  }

  private static Board initializeStartingPosition2() {
    Tile airship = new Tile(TileType.AIRSHIP, 0);
    Tile largeCorner = new Tile(TileType.LARGE_CORNER, 0);
    Tile largeTop = new Tile(TileType.LARGE_BOTTOM, 270);
    Tile smallWhite = new Tile(TileType.SMALL_CORNER, 270);
    Tile smallGrey = new Tile(TileType.SMALL_CORNER, 0);
    Tile smallBlack = new Tile(TileType.SMALL_CORNER, 270);
    Tile empty = new Tile(TileType.EMPTY, 0);
    Tile column = new Tile(TileType.COLUMN, 270);
    Tile diagonal = new Tile(TileType.DIAGONAL, 0);

    return new Board(smallWhite, airship, empty,
        diagonal, column, smallBlack,
        largeTop, largeCorner, smallGrey);
  }
}


