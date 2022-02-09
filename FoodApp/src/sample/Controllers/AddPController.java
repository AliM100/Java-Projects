package sample.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.alerts;

public class AddPController implements Initializable {
	//combobox dropdown items
	ObservableList<String> boxList=FXCollections.observableArrayList("Sandwich","Drink");
	@FXML
	private ComboBox<String> box;
	@FXML
	private Button browse,submit;
	@FXML
	private ImageView image,minimize,exit;
	@FXML
	private AnchorPane pane;
	@FXML
	private TextField nameT,priceT;
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
	public void initialize(URL location, ResourceBundle resources) {
		box.setItems(boxList);
		box.setFocusTraversable(false);
		nameT.setFocusTraversable(false);
		priceT.setFocusTraversable(false);
	}
	@FXML
	public void select(ActionEvent e) {
		FileChooser fc=new FileChooser();
		//select only images extensions
		FileChooser.ExtensionFilter imageFilter= new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fc.getExtensionFilters().add(imageFilter);
		fc.setTitle("select image");
		Stage stage=(Stage) pane.getScene().getWindow();
		File f=fc.showOpenDialog(stage);
		if(f!=null) {
			image.setVisible(true);
			//set imageview to selected image
			image.setImage(new Image(f.toURI().toString()));
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
	@FXML
	public void submit() {
		if(box.getSelectionModel().isEmpty()) {
			alerts.Error("Type Required");
		}
		else{
			if(nameT.getText().isEmpty()) {
				alerts.Error("Name Required!");
			}
			else {
				try {
					if(DB.checkPname(nameT.getText().toString())==true) {
						alerts.Error("There is a Product with same Name\nChoose Another");
						nameT.clear();
					}
					else {
						if(priceT.getText().isEmpty()) {
							alerts.Error("Price Required");
						}
						else{
							if(isNumber(priceT.getText().toString())==false) {
								alerts.Error("Numeric value required in price");
							}
							else{
								if(!image.isVisible()) {
									alerts.Error("Image Required");
								}
								else{
									String type=box.getSelectionModel().getSelectedItem(),name=nameT.getText();
									double price=Double.parseDouble(priceT.getText());
									ImageView im=image;
									addP(type,name,price,im);
									((Stage) submit.getScene().getWindow()).close();
									alerts.Noti("Item Added");
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void addP(String type,String name,double price,ImageView image) {
		try {
			DB.createP(name, type, price, image);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
