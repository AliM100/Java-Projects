package sample.Controllers;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Manager;
import sample.Model.Customer;
import sample.Model.DB;
import sample.Model.Drink;
import sample.Model.Product;
import sample.Model.Sandwich;
import sample.Model.StageFactory;
import sample.Model.Tables;
import sample.Model.temp;
public class TablesController implements Initializable {
	 @FXML
	    private Label c1, c2, c3, c4, c5, c6, c7, c8, c9;

	 ArrayList<Label> c=new ArrayList<Label>();
	 ArrayList<VBox> v=new ArrayList<VBox>();
	    @FXML
	    private GridPane grid;

	    @FXML
	    private Button load;

//	    @FXML
//	    private Label num1, num2, num3,num4, num5,num6, num7, num8,num9;

	    @FXML
	    private VBox v1, v2, v3, v4, v5, v6, v7, v8, v9;
	    static temp temp=new temp();
	    Tables t;
	    static int flag=0;
	    ObservableList<Tables> oblist = FXCollections.observableArrayList();
	    
	    @FXML
	    void clickgrid(MouseEvent event) throws Exception {
	    	  Node clickedNode = event.getPickResult().getIntersectedNode();
	    	    if (clickedNode != grid) {
	    	        // click on descendant node
//	    	        Integer colIndex = GridPane.getColumnIndex(clickedNode);
//	    	        Integer rowIndex = GridPane.getRowIndex(clickedNode);
	    	        String id=clickedNode.getId();
	    	        for (char ch : id.toCharArray()) {
	    	
	    	            if (Character.isDigit(ch)) {
	    	            	temp.settid(Integer.parseInt(String.valueOf(ch)));
	    	            	System.out.println("from sender:"+ch);
	    	               	Parent parent=FXMLLoader.load(getClass().getResource("../Views/edit_table_row.fxml"));
	    	           		  Stage stage=new Stage();
	    	           		  stage.setScene(new Scene(parent));
	    	           		  stage.show();
	    	            	}
	    	            
	    	        }}
	    	    
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		c.add(c1);
		c.add(c2);
		c.add(c3);
		c.add(c4);
		c.add(c5);
		c.add(c6);
		c.add(c7);
		c.add(c8);
		c.add(c9);
   	 ObservableList<Tables> o = FXCollections.observableArrayList();
   	Tables T;
   	try {
		o=DB.getTables();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	int i=0;
   	for ( Node node : grid.getChildren() )
   	{
   		if(i<o.size()) {
   			int tid=o.get(i).getTid();
       		String isreserved=o.get(i).getIsreserved();
       		Label cc=c.get(i);
       		if(isreserved.equals("Yes"))
       		{
       	    (( VBox ) node).setStyle("-fx-background-color: #FF0000;");
       		}else(( VBox ) node).setStyle("-fx-background-color: #4CC417;");
       		cc.setText(String.valueOf(o.get(i).getCapacity()));
       		i++;
   		}
   		
   	}
   	
		
	}
	 @FXML
	    void edit(MouseEvent event) {}
	
    @FXML
    void load(MouseEvent event) throws Exception {
    	 ObservableList<Tables> o = FXCollections.observableArrayList();
    	Tables T;
    	o=DB.getTables();
    	int i=0;
    	for ( Node node : grid.getChildren() )
    	{
    		if(i<o.size()) {
    			int tid=o.get(i).getTid();
        		String isreserved=o.get(i).getIsreserved();
        		Label cc=c.get(i);
        		if(isreserved.equals("Yes"))
        		{
        	    (( VBox ) node).setStyle("-fx-background-color: #FF0000;");
        		}else(( VBox ) node).setStyle("-fx-background-color: #4CC417;");
        		cc.setText(String.valueOf(o.get(i).getCapacity()));
        		i++;
    		}
    		
    	}
    	
    }


   
    
}

	
	