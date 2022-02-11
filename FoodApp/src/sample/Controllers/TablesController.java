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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import sample.Model.Tables;
import sample.Model.temp;
public class TablesController implements Initializable {

    @FXML
    private TableColumn<Tables, Integer> capacity;


    @FXML
    private Button edit;

    @FXML
    private TableColumn<Tables, String> isreserved;

    @FXML
    private TableColumn<Tables, Integer> tid;

    @FXML
    private TableColumn<Tables, String> time;

    @FXML
    private TableView<Tables> table;
    static temp temp=new temp();
    Tables t;
    ObservableList<Tables> oblist = FXCollections.observableArrayList();

    static int flag=0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
    @FXML
    void load(MouseEvent event) throws Exception {

    	tid.setCellValueFactory(new PropertyValueFactory<>("tid"));
    	capacity.setCellValueFactory(new PropertyValueFactory<>("capacity")); 	
    	isreserved.setCellValueFactory(new PropertyValueFactory<>("isreserved"));
    	time.setCellValueFactory(new PropertyValueFactory<>("time"));
    	
    	table.setItems(DB.getTables());
    	
    	
    	
    }

    @FXML
    void clicked(MouseEvent event) {
    	if(event.getClickCount()==1)
    	{
    		flag=1;
    		oblist.clear();
    		t=table.getSelectionModel().getSelectedItem();
    		oblist.add(t);
    	}else flag=0;
    }
    
    @FXML
    void edittable(MouseEvent event) throws Exception {
    	if(flag==1)
    	{
    		if((oblist.get(0).getIsreserved()).equals("No"))
    		{
    			oblist.get(0).setCid(0);
    		}else {
    			oblist.get(0).setCid(DB.getcusid(oblist.get(0).getTid()));
    		}
    		
    		temp.setedit(oblist);
       	Parent parent=FXMLLoader.load(getClass().getResource("../Views/edit_table_row.fxml"));
   		  Stage stage=new Stage();
   		  stage.setScene(new Scene(parent));
   		  stage.show();
    	}
    }

   
    
}

	
	