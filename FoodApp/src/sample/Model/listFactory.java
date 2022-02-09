package sample.Model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controllers.loginController;

public class listFactory {
	public static ObservableList<String> get(String s) {
		ObservableList<String> boxList=FXCollections.observableArrayList();
		if(s.equals("cart")) {
			ArrayList<Cart> pl=DB.getOI(loginController.getUcart());
			for(int i=0;i<pl.size();i++) {
				boxList.add(pl.get(i).getName());
			}
		}
		else if(s.equals("products")) {
			ArrayList<Sandwich> sl=DB.getS();
			ArrayList<Drink> dl=DB.getD();
			for(int i=0;i<sl.size();i++) {
				boxList.add(sl.get(i).getName());
			}
			for(int i=0;i<dl.size();i++) {
				boxList.add(dl.get(i).getName());
			}
		}
		else if(s.equals("client")) {
			ArrayList<Customer> cl=DB.getCus();
			for(int i=0;i<cl.size();i++) {
				boxList.add(cl.get(i).getUsername());
			}
		}
		else if(s.equals("admin")) {
			ArrayList<Manager> al=DB.getManagers();
			for(int i=0;i<al.size();i++) {
				boxList.add(al.get(i).getUsername());
			}
		}
		return boxList;
	}
}
