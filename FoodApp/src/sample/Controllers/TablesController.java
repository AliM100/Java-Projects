package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Customer;
import sample.Model.DB;
import sample.Model.Drink;
import sample.Model.FXMLFactory;
import sample.Model.Manager;
import sample.Model.Product;
import sample.Model.Sandwich;
import sample.Model.StageFactory;
import sample.Model.Tables;
import sample.Model.alerts;
import sample.Model.temp;

public class TablesController implements Initializable {
	
	static temp temp=new temp();
	Tables t;
	static int flag=0;
	@FXML
	private GridPane grid;
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		// TODO Auto-generated method stub
		 ObservableList<Tables> o = FXCollections.observableArrayList();

		   	Tables T;
		   	try {
				o=DB.getTables();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int sz=o.size();
		int column=0;
		int row=0;
		
		if(sz==0) {
			alerts.Noti("No Available Tables");
		}
		else {
			for(int i=0;i<sz;i++) {
				Label tn,cap;
				VBox v;
				HBox h1,h2;
				try {
					AnchorPane n= (AnchorPane) FXMLFactory.get("tabgrid");
					v=(VBox) n.getChildren().get(0);
					h1=(HBox) v.getChildren().get(0);
					tn=(Label) h1.getChildren().get(1);
					tn.setText(o.get(i).getTid()+"");
					h2=(HBox) v.getChildren().get(1);
					cap=(Label) h2.getChildren().get(1);
					cap.setText(o.get(i).getCapacity()+"");
					GridPane.setConstraints(n, column, row);
					grid.getChildren().add(n);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				column++;
				//move to second row
				if(column==3) {
					row++;
					column=0;
				}
			}
			int j=0;
	   	for ( Node node : grid.getChildren() )
	   	{
	   		if(j<o.size()) {
	   		
	       		String isreserved=o.get(j).getIsreserved();
	       		if(isreserved.equals("Yes"))
	       		{
	       	     node.setStyle("-fx-background-color: #FF0000;");
	       		}else node.setStyle("-fx-background-color: #4CC417;");
	       		
	       		j++;
	   		}
	   		
	   	}
		}
	}
	int getNodeByCoordinate(Integer row, Integer column) {
		int i=0;
	    for (Node node : grid.getChildren()) {
	    	i++;
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
	        {
	            return i;
	        }   
	    }
	    return 0;
	}

	@FXML
	void clickgrid(MouseEvent event) throws Exception {
	  Node clickedNode = event.getPickResult().getIntersectedNode();
	    if (clickedNode != grid) {
	        // click on descendant node
	        Integer colIndex = GridPane.getColumnIndex(clickedNode);
	        Integer rowIndex = GridPane.getRowIndex(clickedNode);
	        int id=getNodeByCoordinate(rowIndex,colIndex);
	        System.out.println(id);
	       	temp.settid(id);
	       	Stage s=StageFactory.getstage("tablesGrid");
		    s.show();
		    s.setOnHidden(e->{
		    	refresh();
		    });
	       }
	       
	    }
	 void refresh() {
		 ObservableList<Tables> o = FXCollections.observableArrayList();

		   	Tables T;
		   	try {
				o=DB.getTables();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int sz=o.size();
		int column=0;
		int row=0;
		
		if(sz==0) {
			alerts.Noti("No Available Tables");
		}
		else {
			for(int i=0;i<sz;i++) {
				Label tn,cap;
				VBox v;
				HBox h1,h2;
				try {
					AnchorPane n= (AnchorPane) FXMLFactory.get("tabgrid");
					v=(VBox) n.getChildren().get(0);
					h1=(HBox) v.getChildren().get(0);
					tn=(Label) h1.getChildren().get(1);
					tn.setText(o.get(i).getTid()+"");
					h2=(HBox) v.getChildren().get(1);
					cap=(Label) h2.getChildren().get(1);
					cap.setText(o.get(i).getCapacity()+"");
					GridPane.setConstraints(n, column, row);
					grid.getChildren().add(n);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				column++;
				//move to second row
				if(column==3) {
					row++;
					column=0;
				}
			}
			int j=0;
	   	for ( Node node : grid.getChildren() )
	   	{
	   		if(j<o.size()) {
	   		
	       		String isreserved=o.get(j).getIsreserved();
	       		if(isreserved.equals("Yes"))
	       		{
	       	     node.setStyle("-fx-background-color: #FF0000;");
	       		}else node.setStyle("-fx-background-color: #4CC417;");
	       		
	       		j++;
	   		}
	   		
	   	}
		}
	 }
}


