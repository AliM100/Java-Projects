package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Model.FXMLFactory;
import sample.Model.MyDecoration;
import sample.Model.StageFactory;

public class HomeController implements Initializable{
	@FXML 
	private HBox logo,StatHbox,SandwichHbox,DrinksHbox,MyCartHbox,AdminHbox,LogoutHbox;
	@FXML
    private ImageView exit,minimize;
	@FXML 
	private AnchorPane stack;
	//close scene 
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
    public void open(MouseEvent event) throws IOException {
		stack.getChildren().clear();
		stack.getChildren().add(FXMLFactory.get(((HBox)event.getSource()).getId()));
		MyDecoration.FadeS(stack);
    }
	@FXML
	public void logout(MouseEvent event) throws IOException {
		((Stage) exit.getScene().getWindow()).close();
		Stage s=StageFactory.getstage(((HBox)event.getSource()).getId());
	    s.show();
	}
	//initialize home scene with description in stack anchorpane
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			if(loginController.getadmin()==false) {
				AdminHbox.setVisible(false);
				StatHbox.setVisible(false);
			}
			else{
				MyCartHbox.setVisible(false);
				AdminHbox.setLayoutY(187.0);
				StatHbox.setLayoutY(242.0);
			}
			stack.getChildren().add(FXMLFactory.get("logo"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}