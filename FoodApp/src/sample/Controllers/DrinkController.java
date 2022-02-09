package sample.Controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.Drink;
import sample.Model.FXMLFactory;
import sample.Model.StageFactory;
import sample.Model.alerts;

public class DrinkController implements Initializable {
	private static int f=0;//f to know if customer has the selected item in cart so update quantity only
	@FXML
	private GridPane grid;
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		// TODO Auto-generated method stub
		ArrayList<Drink> dl=DB.getD();
		int sz=dl.size();
		int column=0;
		int row=0;
		if(sz==0) {
			alerts.Noti("No Available Drinks");
		}
		else {
			for(int i=0;i<sz;i++) {
				Label ln,lp;
				ImageView v;
				Button ad;
				try {
					AnchorPane n= (AnchorPane) FXMLFactory.get("products");
					ln=(Label) n.getChildren().get(1);
					ln.setText(dl.get(i).getName());
					lp=(Label) n.getChildren().get(4);
					lp.setText(""+dl.get(i).getPrice()+" L.L");
					v=(ImageView) n.getChildren().get(2);
					v.setImage(dl.get(i).getimage().getImage());
					v.setFitHeight(185);
					v.setFitWidth(260);
					ad=(Button)n.getChildren().get(5);
					if(loginController.getadmin()==true) {
						ad.setVisible(false);
					}
					else{
						ad.setVisible(true);
						ad.setOnAction(e->{
							try {
								if(DB.searchC(DB.getPid(ln.getText()), loginController.getUcart())==true) {
									f=1;
								}
								String []price=lp.getText().split(" ");
								add(ln.getText(),Double.parseDouble(price[0]));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					});
					}
					GridPane.setConstraints(n, column, row);
					grid.getChildren().add(n);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				column++;
				//move to second row
				if(column==2) {
					row++;
					column=0;
				}
			}
		}
	}
	public void add(String dname,double dprice) {
		try {
			Stage s=StageFactory.getstage("addToCart");
		    s.show();
		    s.setOnHidden(e->{
		    	try {
		    		if(addToCartController.getFlag()==1) {
		    			int q=addToCartController.getQ();
		    			if(q!=0) {
		    				if(f==1) {
					    		DB.updateC(loginController.getUcart(), DB.getPid(dname),q);
					    		f=0;
					    	}
					    	else{
					    		DB.addPC(loginController.getUcart(),DB.getPid(dname),q);
					    	}
					    	alerts.Noti("Added to Cart");
					    }
					    addToCartController.setFlag(0);
		    		}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int getF() {
		return f;
	}
}
