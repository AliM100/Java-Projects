package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Model.Cart;
import sample.Model.DB;
import sample.Model.StageFactory;

public class CartController implements Initializable {
	@FXML
	private Button x,pC;
	@FXML
	private TableView<Cart> table;
	@FXML
	private TableColumn<Cart,String> catagory,name;
	@FXML
	private TableColumn<Cart,Double> price;
	@FXML
	private TableColumn<Cart,Integer> quantity;
	@FXML
	public void tipRemove() {
		Tooltip tt = new Tooltip();
		tt.setText("remove product");
		x.setTooltip(tt);
	}
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		table.setFocusTraversable(false);
		x.setFocusTraversable(false);
		pC.setFocusTraversable(false);
		ArrayList<Cart> cl;
		cl=DB.getOI(loginController.getUcart());
		if(cl.size()==0) {
			pC.setVisible(false);
			x.setVisible(false);
		}
		else{
			pC.setVisible(true);
			x.setVisible(true);
			catagory.setCellValueFactory(new PropertyValueFactory<Cart,String>("type"));
			name.setCellValueFactory(new PropertyValueFactory<Cart,String>("name"));
			price.setCellValueFactory(new PropertyValueFactory<Cart, Double>("price"));
			quantity.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("quantity"));
			for(int i=0;i<cl.size();i++) {
				 table.getItems().add(new Cart(cl.get(i).getName(),cl.get(i).getType(),cl.get(i).getPrice(),cl.get(i).getQuantity()));
			}
		}
	}
	public void refresh() {
		table.setFocusTraversable(false);
		x.setFocusTraversable(false);
		pC.setFocusTraversable(false);
		table.getItems().clear();
		ArrayList<Cart> cl;
		cl=DB.getOI(loginController.getUcart());
		if(cl.size()==0) {
			pC.setVisible(false);
			x.setVisible(false);
		}
		for(int i=0;i<cl.size();i++) {
			 table.getItems().add(new Cart(cl.get(i).getName(),cl.get(i).getType(),cl.get(i).getPrice(),cl.get(i).getQuantity()));
		}
	}
	@FXML
	public void remove(MouseEvent event) {
	 try {   
		Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
	    s.show();
	    s.setOnHidden(e->{
	    	refresh();
	    });
	 } catch (IOException e) {
		e.printStackTrace();
	 }
	}
	@FXML
	public void PTC(MouseEvent event) {
		try {   
			Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
		    s.show();
		    s.setOnHidden(e->{
		    	refresh();
		    });
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	}
}
