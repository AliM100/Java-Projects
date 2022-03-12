package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.alerts;

public class addtableController {

	@FXML
    private ImageView exit,minimize;
	//close scene 
	@FXML
	public void closeAction() {
		((Stage) exit.getScene().getWindow()).close();
	}
	//minimize scene 
	@FXML
	public void miniAction() {
		((Stage) exit.getScene().getWindow()).setIconified(true);
	}
    @FXML
    private Spinner<Integer> cap;

    @FXML
    private Button save;

    @FXML
    void Save(MouseEvent event) throws NumberFormatException, Exception {
   
    	 DB.addTable(cap.getValue());
    	 closeAction();
    }

}


