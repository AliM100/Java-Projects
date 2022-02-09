package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.StageFactory;
import sample.Model.alerts;

public class loginController implements Initializable {
	private static boolean admin;
	private static int Ucart;
	private static String usern;
	private static int cid;
	@FXML
	private TextField userN;
	@FXML
	private PasswordField pass;
	@FXML
	private ImageView minimize,exit;
	@FXML
	private Button loginB;
	@FXML
	private Label fpass,signUp;
	@FXML
	public void closeAction() {
		((Stage) pass.getScene().getWindow()).close();
	}
	//minimize scene 
	@FXML
	public void miniAction() {
		((Stage) userN.getScene().getWindow()).setIconified(true);
	}
	@FXML
	public void signup(MouseEvent event) throws IOException {
		((Stage) pass.getScene().getWindow()).close();
		Stage s=StageFactory.getstage(((Label)event.getSource()).getId());
		s.show();
	}
	@FXML
	public void changepass(MouseEvent event) throws IOException {
		((Stage) pass.getScene().getWindow()).close();
		Stage s=StageFactory.getstage(((Label)event.getSource()).getId());
		s.show();
	}
	@FXML
	public void login(MouseEvent event) {
		if(userN.getText().isEmpty()) {
			alerts.Error("Username Required");
		}else {
			if(pass.getText().isEmpty()) {
				alerts.Error("Password Required");
			}else {
				try {
					//no such user with enetered pass
					if(DB.login(userN.getText(), pass.getText(),"Manager")==false && DB.login(userN.getText(), pass.getText(),"Client")==false){
						alerts.Error("Incorrect Username and/or Password!");
						pass.clear();
						userN.clear();
					}
					else {
						try {
							//client
							if(DB.login(userN.getText(), pass.getText(),"Client")==true) {
								admin=false;
								Ucart=DB.getOid(DB.getCid(userN.getText()));
								cid=DB.getCid(userN.getText());
							}
							else {
								//admin
								admin=true;
							}
							((Stage) pass.getScene().getWindow()).close();
							usern=userN.getText();
							Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
							s.show();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	public static boolean getadmin() {
		return admin;
	}
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		if(SignUpController.getSignup()==1) {
			userN.setText(SignUpController.getN());
			pass.setText(SignUpController.getP());
			SignUpController.setSignup(0);
		}
		userN.setFocusTraversable(false);
		pass.setFocusTraversable(false);
	}
	public static int getUcart() {
		return Ucart;
	}
	public static int getCid() {
		return cid;
	}
	public static String getUsern() {
		return usern;
	}
}
