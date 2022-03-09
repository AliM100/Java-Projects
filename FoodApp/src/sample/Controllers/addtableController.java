package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Model.DB;
import sample.Model.alerts;

public class addtableController {


    @FXML
    private Spinner<Integer> cap;

    @FXML
    private Button save;

    @FXML
    void Save(MouseEvent event) throws NumberFormatException, Exception {
   
    	 DB.addTable(cap.getValue());
    }

}


