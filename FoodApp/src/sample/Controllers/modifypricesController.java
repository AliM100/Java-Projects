package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Model.alerts;
import sample.Model.modifypricesModel;

public class modifypricesController {
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
    		closeAction();
    	}
    }

}


