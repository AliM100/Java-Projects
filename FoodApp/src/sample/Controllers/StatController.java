package sample.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.Model.DB;

public class StatController implements Initializable{
	@FXML
	private Label tc,tp,to;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			tc.setText(""+DB.getCnbs());
			to.setText(""+DB.getTO());
			tp.setText(""+DB.getPnbs());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
