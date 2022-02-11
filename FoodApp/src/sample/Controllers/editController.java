package sample.Controllers;




import java.util.ArrayList;

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
    
    	ObservableList<Tables> ol=t.getedit();
    	T=ol.get(0);
    	if(isreserved.isSelected())
    	{
    		T.setIsreserved("Yes");
    	}
    	else T.setIsreserved("No");
    	if(custname.getText().isEmpty() && custtel.getText().isEmpty()) {
			T.setCid(0);
		}else {
			T.setCid(DB.getcustid(custname.toString(),custtel.toString()));
		}
    	T.setTime(time.getText());
    	DB.updatetable(T);
    }
    
    @FXML
    void initialize() throws Exception {
    	
    	ObservableList<Tables> table;
    	table=t.getedit();
    	tabnum.setText(""+table.get(0).getTid());
    	tabcapacity.setText(""+table.get(0).getCapacity());
    	if(table.get(0).getCid()!=0)
    	{
    		ArrayList <String> a=DB.getcustnametel(table.get(0).getCid());
    		custname.setText(a.get(0));
    		custtel.setText(a.get(1));
    	}
    	if((table.get(0).getIsreserved()).equals("Yes"))
    	{
    		isreserved.setIndeterminate(true);
    	}
    	time.setText(table.get(0).getTime());
    }
    
    
}
