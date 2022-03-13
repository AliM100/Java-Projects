package sample.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.Model.DB;

public class StatController implements Initializable {
    @FXML
    private Label tc, tp, to;

    @FXML
    private DatePicker finishDate,startDate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            tc.setText("" + DB.getCnbs());
            to.setText("" + DB.getTO());
            tp.setText("" + DB.getPnbs());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void search(ActionEvent event) {
        if(finishDate!=null && startDate !=null) {
            try {
                tc.setText("" + DB.getCnbs(startDate.getValue(),finishDate.getValue()));
                to.setText("" + DB.getTO(startDate.getValue(),finishDate.getValue()));
                tp.setText("" + DB.getPnbs(startDate.getValue(),finishDate.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                tc.setText("" + DB.getCnbs());
                to.setText("" + DB.getTO());
                tp.setText("" + DB.getPnbs());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}