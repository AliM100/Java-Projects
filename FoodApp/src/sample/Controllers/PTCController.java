package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.StageFactory;
import sample.Model.alerts;

public class PTCController implements Initializable {
	ObservableList<String> boxList=FXCollections.observableArrayList("Cash On Delivery","Credit Card");
	@FXML
	private ComboBox<String> box;
	@FXML
	private TextField totalP,loc,tel;
	@FXML
	private ImageView minimize,exit;
	@FXML
	private Button ccb;
	@FXML
	public void closeAction() {
		((Stage) exit.getScene().getWindow()).close();
	}
	//minimize scene 
	@FXML
	public void miniAction() {
		((Stage) exit.getScene().getWindow()).setIconified(true);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		box.setItems(boxList);
		box.setFocusTraversable(false);
		totalP.setFocusTraversable(false);
		loc.setFocusTraversable(false);
		tel.setFocusTraversable(false);
		try {
			totalP.setText("Total Price: "+DB.getOTP(loginController.getUcart())+" L.L");
			String[] LT=DB.getinfo(loginController.getCid());
			if(LT[0]!=null) {
				loc.setText(LT[0]);
				tel.setText(LT[1]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	public void check() throws Exception {
		if(box.getSelectionModel().getSelectedItem().equals("Credit Card")) {
			if(DB.checkCC(loginController.getCid())==false) {
				Stage s=StageFactory.getstage("credit");
				s.show();
				s.setOnHidden(e->{
					try {
						if(DB.checkCC(loginController.getCid())==false) {
							//if closed with no change select cash on delivery
							box.getSelectionModel().selectPrevious();
						}
						else {
							ccb.setVisible(true);
							ccb.setOnAction(event->{
								try {
									Stage l = StageFactory.getstage("credit");
									l.show();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							});
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
			else {
				ccb.setVisible(true);
				ccb.setOnAction(e->{
					try {
						Stage n = StageFactory.getstage("credit");
						n.show();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		}
		else {
			ccb.setVisible(false);
		}
	}
	@FXML
	public void oD() {
		if(loc.getText().isEmpty()) {
			alerts.Error("Location Required");
		}
		else {
			if(check(loc.getText())==false) {
				alerts.Error("Location can be composed of letters and/or spaces only");
				loc.clear();
			}
			else {
				if(tel.getText().isEmpty()) {
					alerts.Error("Telephone Required");
				}
				else{
					int length = String.valueOf(tel.getText()).length();
					if(length!=8) {
						alerts.Error("Telephone Number must be of 8 digits");
						tel.clear();
					}
					else {
						if(box.getSelectionModel().isEmpty()) {
							alerts.Error("Method Required");
						}
						else {
							try {
								DB.setLT(DB.getDid(loginController.getUcart()), loc.getText(),Integer.parseInt(tel.getText()));
								alerts.Noti("Thank you for your confidence and choosing AbuAliWay");
								DB.insertO(loginController.getCid());
					            DB.clearOI(loginController.getUcart());
					            ((Stage) box.getScene().getWindow()).close();
							} catch (NumberFormatException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} catch (Exception e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	public boolean check(String s) {
	   int len = s.length(),ctr=0;
	   for (int i = 0; i < len; i++)
	      if (Character.isLetter(s.charAt(i)) == false){
	    	  //not letter
	    	  if(s.charAt(i)!=' ') {
	    		  //not space
	    		  return false;
	    	  }
	    	  else {
	    		  ctr++;
	    	  }
	      }
	   if(ctr==len)
		   return false;
	   return true;
	}
}
