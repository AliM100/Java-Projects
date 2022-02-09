package sample.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class addToCartController implements Initializable{
	private static int quan=0,flag=0;//flag to know if customer pressed add button to perform adding to cart in the previous page
	@FXML
	private Spinner<Integer> quantity;
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
	@FXML
	public void addC() {
		flag=1;
		quan=quantity.getValue();
		((Stage) quantity.getScene().getWindow()).close();
	}
	public static int getQ() {
		return quan;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		quantity.setFocusTraversable(false);
	}
	public static int getFlag() {
		return flag;
	}
	public static void setFlag(int i) {
		flag=i;
	}
}

