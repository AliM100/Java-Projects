package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Model.alerts;
import sample.Model.modifypricesModel;

public class modifypricesController {

    @FXML
    private TextField new$;

    @FXML
    private TextField old$;

    @FXML
    private Button save;

    @FXML
    void Save(MouseEvent event) {
    	if (old$.getText().isEmpty() || new$.getText().isEmpty())
    	{
    		alerts.Noti("Empty Fields");
    	}
    	else {
    		float oldp=Float.valueOf(old$.getText());
    		float newp=Float.valueOf(new$.getText());
    		modifypricesModel m =new modifypricesModel(oldp,newp);
    		m.modify();
    	}
    }

}


