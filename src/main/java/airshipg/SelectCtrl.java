package airshipg;

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

public class SelectCtrl implements Initializable {

    private TileContainer tileContainer = TileContainer.getInstance();
    private TileType tileType;
    private MainCtrl mainCtrl;

    @FXML
    public HBox tipesbox;
    @FXML
    public MenuButton mButton;


    public void done() {
        try {
            tileContainer.setTile(new Tile(tileType, Integer.parseInt(mButton.getText())));
//        clearFields();
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
//        tipesbox.getChildren().addAll(new Button("airship"), new Button("small corner"), new Button("largecorner"), new Label("empty"), new Label("largebottom"), new Label("diagonal"), new Label("column"));
    }

    public void setCtrls(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void choseair() {
        tileType = (TileType.AIRSHIP);
    }

    public void chosesc() {
        tileType = (TileType.SMALL_CORNER);
    }

    public void choselc() {
        tileType = (TileType.LARGE_CORNER);
    }

    public void chosee() {
        tileType = (TileType.EMPTY);
    }

    public void choselb() {
        tileType = (TileType.LARGE_BOTTOM);
    }

    public void chosed() {
        tileType = (TileType.DIAGONAL);
    }

    public void chosec() {
        tileType = (TileType.COLUMN);
    }
}