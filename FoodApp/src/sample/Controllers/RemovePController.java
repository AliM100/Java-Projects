package sample.Controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Model.DB;
import sample.Model.alerts;
import sample.Model.listFactory;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class RemovePController implements Initializable {
	//combobox dropdown items
	ObservableList<String> boxList=FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> box;
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
	public void remove() {
		if(box.getSelectionModel().isEmpty()) {
			alerts.Error("Product name Required");
		}
		else{
			try {
				DB.removeP(DB.getPid(box.getSelectionModel().getSelectedItem()));
				alerts.Noti("Remove Complete");
				((Stage) exit.getScene().getWindow()).close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		boxList=listFactory.get("products");
		box.setItems(boxList);
		box.setFocusTraversable(false);
	}
}
