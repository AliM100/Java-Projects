package sample.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.alerts;

public class CreditCardController implements Initializable {
	ObservableList<String> boxList=FXCollections.observableArrayList("Master","Visa","Paypal");
	@FXML
	private ComboBox<String> box;
	@FXML
	private TextField ccnb;
	@FXML
	private ImageView minimize,exit;
	@FXML
	public void closeAction() {
		((Stage) exit.getScene().getWindow()).close();
	}
	//minimize scene 
	@FXML
	public void miniAction() {
		((Stage) exit.getScene().getWindow()).setIconified(true);
	}
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		// TODO Auto-generated method stub
		box.setItems(boxList);
		ccnb.setFocusTraversable(false);
		box.setFocusTraversable(false);
		try {
			//if have credit card set creditcard nb and type
			if(DB.checkCC(loginController.getCid())==true) {
				String cc[]=DB.getCC(loginController.getCid());
				ccnb.setText(""+cc[1]);
				box.getSelectionModel().select(cc[0]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
	public void save() {
		if(ccnb.getText().isEmpty()) {
			alerts.Error("Credit Card number Required");
		}
		else {
			if(isNumber(ccnb.getText().toString())==false) {
				alerts.Error("Numeric value required in Credit Card Number");
				ccnb.clear();
			}
			else {
				try {
					if(DB.checkCCnba(Integer.parseInt(ccnb.getText()),loginController.getCid())) {
						alerts.Error("Credit Card attached to another Account");
						ccnb.clear();
					}
					else{
						if(box.getSelectionModel().isEmpty()) {
							alerts.Error("Credit Card Type Required");
						}
						else {
							//no credit card create one
							if(DB.checkCC(loginController.getCid())==false) {
								DB.createCredit(loginController.getCid(), box.getSelectionModel().getSelectedItem(), Integer.parseInt(ccnb.getText()));
							}
							else {
								//update credit card
								DB.setCredit(loginController.getCid(), box.getSelectionModel().getSelectedItem(), Integer.parseInt(ccnb.getText()));
							}
							((Stage) exit.getScene().getWindow()).close();
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static boolean isNumber(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException nfe) {
	    	return false;
	    }
	}
}
