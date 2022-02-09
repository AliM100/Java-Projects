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
import sample.Model.Customer;
import sample.Model.DB;
import sample.Model.Manager;
import sample.Model.StageFactory;
import sample.Model.alerts;
import javafx.stage.Stage;

public class SignUpController implements Initializable{
	private static String n,p;
	private static int signup;//to check that if signup complete put entered values to login page
	@FXML
	private TextField name,email;
	@FXML
	private PasswordField pass,Cpass;
	@FXML
	private ImageView minimize,exit;
	@FXML
	private HBox back;
	@FXML
	private Button create;
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
	public void add(MouseEvent event) {
		if(name.getText().isEmpty()) {
			alerts.Error("Username Required");
		}
		else {
			try {
				if(DB.checkname(name.getText())==true) {
					alerts.Error("Username Exist\nChoose Another");
					name.clear();
				}
				else {
					if(email.getText().isEmpty()) {
						alerts.Error("Email Required");
					}
					else {
						if(validEmail(email.getText())==false) {
							alerts.Error("Valid Email Required");
						}
						else {
							if(!DB.checkEmail(email.getText())[0].equals("0")) {
								alerts.Error("Email is already in use\nChoose Another");
								email.clear();
							}
							else {
								if(pass.getText().isEmpty()) {
									alerts.Error("Password Required");
								}
								else {
									if(Cpass.getText().isEmpty()) {
										alerts.Error("Confirm Password");
									}
									else{
										if(!pass.getText().equals(Cpass.getText())) {
											alerts.Error("Password mismatch!");
											Cpass.clear();
										}
										else {
											//admin creation
											if(AdminController.getCreateAdmin()==1) {
												DB.createManager(new Manager(name.getText(), pass.getText(),email.getText()));
												alerts.Noti("Account Created");
												AdminController.setCreateAdmin(0);
												((Stage) pass.getScene().getWindow()).close();
											}
											else{
												//client creation
												n=name.getText();
												p=pass.getText();
												signup=1;
												alerts.Noti("Account Created");
												DB.createClient(new Customer(name.getText(), pass.getText(),email.getText()));
												((Stage) pass.getScene().getWindow()).close();
												Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
												s.show();
											}
										}
									}
								}
							}
						}
					} 
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
	@FXML
	public void back(MouseEvent event) throws IOException {
		((Stage) pass.getScene().getWindow()).close();
		Stage s=StageFactory.getstage(((HBox)event.getSource()).getId());
		s.show();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if(AdminController.getCreateAdmin()==1) {
			back.setVisible(false);
		}
		else back.setVisible(true);
		name.setFocusTraversable(false);
		pass.setFocusTraversable(false);
		Cpass.setFocusTraversable(false);
		email.setFocusTraversable(false);
	}
	public static String getN() {
		return n;
	}
	public static String getP() {
		return p;
	}
	public static int getSignup() {
		return signup;
	}
	public static void setSignup(int i) {
		signup=i;
	}
	static boolean validEmail(String email) {
	    return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
}
