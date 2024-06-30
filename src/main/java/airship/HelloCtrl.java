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

    public Tile setGeneric(Tile tile, Button button) {
        mainCtrl.showSelect();
        tile = tileContainer.getTile();
        String  imageName =  tile.getType().getImageName();
        System.out.println(imageName);
        URL url = this.getClass().getResource(imageName);
        System.out.println(url);
        button.setGraphic(new ImageView(new Image(url.toString())));
        return tile;
    }

//    public void setBackground(Background background) {
//        gPane.setBackground(background);
//    }

    public void set31() {
        x2y0 = setGeneric(x2y0, b31);
//        x2y0 = setType();
//        URL url = getClass().getClassLoader().getResource("airshipg/" + x2y0.getType().getImageName());
//        System.out.println(url);
//        b31.setGraphic(new ImageView(new Image(url.toString())));
    }

    public void set32() {
        x2y1 = setGeneric(x2y1, b32);
//        x2y1 = setType();
//        b32.setText(x2y1.toString());
    }


    public void set33() {
        x2y2 = setGeneric(x2y2, b33);
//        x2y2 = setType();
//        b33.setText(x2y2.toString());

    }

    public void set21() {
        x1y0 = setGeneric(x1y0, b21);
//        x1y0 = setType();
//        b21.setText(x1y0.toString());

    }

    public void set22() {
        x1y1 = setGeneric(x1y1, b22);
//        x1y1 = setType();
//        b22.setText(x1y1.toString());

    }


    public void set23() {
        x1y2 = setGeneric(x1y2, b23);
//        x1y2 = setType();
//        b23.setText(x1y2.toString());

    }

    public void set11() {
        x0y0 = setGeneric(x0y0, b11);
//        x0y0 = setType();
//        b11.setText(x0y0.toString());

    }

    public void set12() {
        x0y1 = setGeneric(x0y1, b12);
//        x0y1 = setType();
//        b12.setText(x0y1.toString());

    }


    public void set13() {
        x0y2 = setGeneric(x0y2, b13);
//        x0y2 = setType();
//        b13.setText(x0y2.toString());

    }

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

    public void handleMouseClick(MouseEvent event, Button button, Tile tile) {
        if (event.getButton() == MouseButton.SECONDARY) { // Check if right mouse button is clicked
            System.out.println("right");
            ImageView imageView = (ImageView) button.getGraphic();
            imageView.setRotate(imageView.getRotate() + 90);
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