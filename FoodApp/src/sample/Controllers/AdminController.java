package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Model.Manager;
import sample.Model.Customer;
import sample.Model.DB;
import sample.Model.Drink;
import sample.Model.Product;
import sample.Model.Sandwich;
import sample.Model.StageFactory;

public class AdminController implements Initializable {
	private static int flag=-1,createAdmin=0;//create admin only to differentiate the signup for customer and admin (admin no back button)
	//flag to differentiate between client and admin removing 1 admin 0 client
	@FXML
	private Button add,addAd,remove,removeAd,removeCus;
	@FXML
	private TableView<Product> table;
	@FXML
	private TableColumn<Product,String> catagory,name;
	@FXML
	private TableColumn<Product,Double> price;
	//add hint
	@FXML
	public void tipAdd() {
		Tooltip tt = new Tooltip();
		tt.setText("Add new product");
		add.setTooltip(tt);
	}
	//remove hint
	@FXML 
	public void tipRemove() {
		Tooltip tt = new Tooltip();
		tt.setText("remove product");
		remove.setTooltip(tt);
	}
	//add product
	@FXML
	public void add(MouseEvent event) {
		try {
			Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
		    s.show();
		    s.setOnHidden(e->{
		    	refresh("add");
		    });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void remove(MouseEvent event) {
		try {
			Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
		    s.show();
		    s.setOnHidden(e->{
		    	refresh("remove");
		    });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void refresh(String s) {
		table.setFocusTraversable(false);
		add.setFocusTraversable(false);
		remove.setFocusTraversable(false);
		removeAd.setFocusTraversable(false);
		removeCus.setFocusTraversable(false);
		//only refresh table if product removed
		if(s.equals("add") || s.equals("remove")) {
			table.getItems().clear();
			ArrayList<Sandwich> sl;
			ArrayList<Drink> dl;
			sl=DB.getS();
			dl=DB.getD();
			if(sl.size()==0 && dl.size()==0) {
				remove.setVisible(false);
			}
			else{
				remove.setVisible(true);
				catagory.setCellValueFactory(new PropertyValueFactory<Product,String>("type"));
				name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
				price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
				for(int i=0;i<sl.size();i++) {
					 table.getItems().add(new Product("Sandwich",sl.get(i).getName(),sl.get(i).getPrice()));
				}
				for(int i=0;i<dl.size();i++) {
					table.getItems().add(new Product("Drink",dl.get(i).getName(),dl.get(i).getPrice()));
				}
			}
		}
		//only refresh admin if admin removed
		if(s.equals("removeAd")||s.equals("addAd")) {
			ArrayList<Manager> al=DB.getManagers();
			try {
				if(DB.isMaster(loginController.getUsern())==true) {
					addAd.setVisible(true);
					if(al.size()==0)
						removeAd.setVisible(false);
					else removeAd.setVisible(true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//only refresh client if client removed
		if(s.equals("removeCus")) {
			ArrayList<Customer> cl=DB.getCus();
			if(cl.size()==0) {
				removeCus.setVisible(false);
			}
			else {
				removeCus.setVisible(true);
			}
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		table.setFocusTraversable(false);
		add.setFocusTraversable(false);
		remove.setFocusTraversable(false);
		removeAd.setFocusTraversable(false);
		removeCus.setFocusTraversable(false);
		ArrayList<Sandwich> sl;
		ArrayList<Drink> dl;
		ArrayList<Manager> al=DB.getManagers();
		ArrayList<Customer>cl=DB.getCus();
		sl=DB.getS();
		dl=DB.getD();
		if(cl.size()==0) {
			removeCus.setVisible(false);
		}
		try {
			if(DB.isMaster(loginController.getUsern())==true) {
				addAd.setVisible(true);
				if(al.size()==0)
					removeAd.setVisible(false);
				else removeAd.setVisible(true);
			}
			else {
				addAd.setVisible(false);
				removeAd.setVisible(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sl.size()==0 && dl.size()==0) {
			remove.setVisible(false);
		}
		else{
			remove.setVisible(true);
			catagory.setCellValueFactory(new PropertyValueFactory<Product,String>("type"));
			name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
			price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
			for(int i=0;i<sl.size();i++) {
				 table.getItems().add(new Product("Sandwich",sl.get(i).getName(),sl.get(i).getPrice()));
			}
			for(int i=0;i<dl.size();i++) {
				table.getItems().add(new Product("Drink",dl.get(i).getName(),dl.get(i).getPrice()));
			}
		}
	}
	@FXML
	public void addAdmin(MouseEvent event) {
		try {
			setCreateAdmin(1);
			Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
		    s.show();
		    s.setOnHidden(e->{
		    	refresh("addAd");
		    	setCreateAdmin(0);
		    });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void removeAdmin(MouseEvent event) {
		try {
			flag=1;
			Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
		    s.show();
		    s.setOnHidden(e->{
		    	refresh("removeAd");
		    });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void removeClient(MouseEvent event) {
		try {
			flag=0;
			Stage s=StageFactory.getstage(((Button)event.getSource()).getId());
		    s.show();
		    s.setOnHidden(e->{
		    	refresh("removeCus");
		    });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int getFlag() {
		return flag;
	}
	public static void setFlag(int flag) {
		AdminController.flag = flag;
	}
	public static int getCreateAdmin() {
		return createAdmin;
	}
	public static void setCreateAdmin(int createAdmin) {
		AdminController.createAdmin = createAdmin;
	}
}
