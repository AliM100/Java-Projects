package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.StageFactory;
import sample.Model.alerts;

public class CpassController implements Initializable{
	private static String[] id=new String[2];
	@FXML
	private TextField email;
	@FXML
	private Button check,change;
	@FXML
	private HBox back;
	@FXML
	private PasswordField pass,cpass;
	@FXML
	private ImageView minimize,exit;
	@FXML
	public void closeAction() {
		((Stage) exit.getScene().getWindow()).close();
	}
	@FXML
	public void miniAction() {
		((Stage) exit.getScene().getWindow()).setIconified(true);
	}
	@FXML
	public void back(MouseEvent event) throws IOException {
		((Stage) pass.getScene().getWindow()).close();
		Stage s=StageFactory.getstage(((HBox)event.getSource()).getId());
		s.show();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		email.setFocusTraversable(false);
		check.setFocusTraversable(false);
		pass.setFocusTraversable(false);
		cpass.setFocusTraversable(false);
		change.setFocusTraversable(false);
	}
	@FXML
    void change(MouseEvent event) throws IOException {
		if(pass.getText().isEmpty()) {
			alerts.Error("New Password Required");
		}
		else {
			if(cpass.getText().isEmpty()) {
				alerts.Error("Confirm Your New Password");
			}
			else {
				if(!cpass.getText().equals(pass.getText())) {
					alerts.Error("Password mismatch!");
					cpass.clear();
				}
				else {
					DB.changeP(id[0], id[1], pass.getText());
					alerts.Noti("Password Changed");
					((Stage) exit.getScene().getWindow()).close();
					Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
					s.show();
				}
			}
		}
    }
    @FXML
    void validate(MouseEvent event) {
    	if(email.getText().isEmpty()) {
    		alerts.Error("Email Required");
    	}
    	else{
    		if(validEmail(email.getText())==false) {
				alerts.Error("Valid Email Required");
				email.clear();
			}
    		else {
    			try {
        			id=DB.checkEmail(email.getText());
    				if(getId()[0].equals("0")){
    					email.clear();
    					alerts.Error("No Such Account");
    				}
    				else {
    					email.setVisible(false);
    					check.setVisible(false);
    					pass.setVisible(true);
    					cpass.setVisible(true);
    					change.setVisible(true);
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
    static boolean validEmail(String email) {
	    return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
	public static String[] getId() {
		return id;
	}
}
