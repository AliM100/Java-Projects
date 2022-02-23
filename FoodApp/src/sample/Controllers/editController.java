package sample.Controllers;




import java.util.ArrayList;

import javax.annotation.processing.SupportedSourceVersion;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.Tables;
import sample.Model.temp;

public class editController {

    @FXML
    private TextField custname;

    @FXML
    private TextField custtel;

    @FXML
    private ImageView exit;

    @FXML
    private CheckBox isreserved;

    @FXML
    private ImageView minimize;

    @FXML
    private Button save;

    @FXML
    private Text tabcapacity;

    @FXML
    private Text tabnum;

    @FXML
    private TextField time;
    static temp t;
    Tables  T;
  
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
    void Save(MouseEvent event) throws Exception {
    
    	
    	T=DB.getTable(temp.gettid());
    	if(isreserved.isSelected())
    	{
    		T.setIsreserved("Yes");
    	}
    	else T.setIsreserved("No");
    	if(custname.getText().isEmpty() && custtel.getText().isEmpty()) {
			T.setCid(0);
		}else {
			
			int cid=DB.getcustid(custname.getText(),custtel.getText());
		
			T.setCid(cid);
		}
    	T.setTime(time.getText());
    	DB.updatetable(T);
    }
    
    @FXML
    void initialize() throws Exception {
    	
    	 int tid=temp.gettid();
    	System.out.println(tid);
    	Tables table;
    	table=DB.getTable(tid);
    	tabnum.setText(""+tid);
    	tabcapacity.setText(""+table.getCapacity());
    	if(table.getCid()!=0)
    	{
    		ArrayList <String> a=DB.getcustnametel(table.getCid());
    		custname.setText(a.get(0));
    		custtel.setText(a.get(1));
    	}
    	if((table.getIsreserved()).equals("Yes"))
    	{
    		isreserved.setIndeterminate(true);
    	}
    	time.setText(table.getTime());
    }
    
    
}
