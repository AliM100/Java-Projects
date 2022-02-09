package sample.Controllers;

import java.awt.event.ActionEvent;
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
import sample.Model.Tables;
public class TablesController implements Initializable {
	sample.Model.DB db;
    @FXML
    private TableColumn<Tables, Integer> capacity;


    @FXML
    private Button edit;

    @FXML
    private TableColumn<Tables, String> isreserved;

    @FXML
    private TableColumn<Tables, Integer> number;

    @FXML
    private TableColumn<Tables, String> reservtime;

    @FXML
    private TableView<Tables> table;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
    @FXML
    void load(ActionEvent event) throws Exception {

    	number.setCellValueFactory(new PropertyValueFactory<>("tabid"));
    	capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    	reservtime.setCellValueFactory(new PropertyValueFactory<>("reservtime"));
    	isreserved.setCellValueFactory(new PropertyValueFactory<>("isreserved"));
    	table.setItems(db.getTables());
    	
    }
    
    
    @FXML
    void edit(ActionEvent event) {

    }

    @FXML
    void removeAdmin(MouseEvent event) {

    }
    
}

	
	