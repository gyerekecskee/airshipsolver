package airshipg;

import java.util.NoSuchElementException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class HelloCtrl {

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

    public void setBackground(Background background) {
        gPane.setBackground(background);
    }

    public void set31() {
        x2y0 = setType();
        b31.setText(x2y0.toString());
    }

    public void set32() {
        x2y1 = setType();
        b32.setText(x2y1.toString());
    }


    public void set33() {
        x2y2 = setType();
        b33.setText(x2y2.toString());

    }

    public void set21() {
        x1y0 = setType();
        b21.setText(x1y0.toString());

    }

    public void set22() {
        x1y1 = setType();
        b22.setText(x1y1.toString());

    }


    public void set23() {
        x1y2 = setType();
        b23.setText(x1y2.toString());

    }

    public void set11() {
        x0y0 = setType();
        b11.setText(x0y0.toString());

    }

    public void set12() {
        x0y1 = setType();
        b12.setText(x0y1.toString());

    }


    public void set13() {
        x0y2 = setType();
        b13.setText(x0y2.toString());

    }


    public Tile setType() {
        mainCtrl.showSelect();
        return tileContainer.getTile();
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
}