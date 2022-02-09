package sample.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.alerts;
import sample.Model.listFactory;

public class removeAorC implements Initializable {
	//combobox dropdown items
	ObservableList<String> boxList=FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> box;
	@FXML
	private ImageView minimize,exit;
	@FXML
	public void closeAction() {
		//if close without doing anything reset flag
		AdminController.setFlag(-1);
		((Stage) exit.getScene().getWindow()).close();
	}
	@FXML
	public void miniAction() {
		((Stage) exit.getScene().getWindow()).setIconified(true);
	}
	@FXML
	public void remove() {
		String un=box.getSelectionModel().getSelectedItem();
		if(box.getSelectionModel().isEmpty()) {
			alerts.Error("Username Required");
		}else {
			try {
				if(AdminController.getFlag()==1) {
					DB.removeManager(un);
					alerts.Noti("Admin Removed");
				}
				else if(AdminController.getFlag()==0) {
					DB.deletecus(un);
					alerts.Noti("Client Removed");
				}
				//reset flag
				AdminController.setFlag(-1);
				((Stage) exit.getScene().getWindow()).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(AdminController.getFlag()==1) {
			//remove admin clicked
			box.setPromptText("Select admin name");
			boxList=listFactory.get("admin");
		}
		else if(AdminController.getFlag()==0) {
			//remove client clicked
			box.setPromptText("Select client name");
			boxList=listFactory.get("client");
		}
		box.setItems(boxList);
		box.setFocusTraversable(false);
	}
}
