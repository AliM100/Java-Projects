package sample.Model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StageFactory {
	public static Stage getstage(String s) throws IOException {
		Stage stage = new Stage();
	    if(s.equals("x"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/RemoveC.fxml"))));
	    else if(s.equals("pC"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/PTC.fxml"))));
	    else if(s.equals("add"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/AddP.fxml"))));
	    else if(s.equals("remove"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/RemoveP.fxml"))));
	    else if(s.equals("addAd")||s.equals("signUp"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/SignUp.fxml"))));
	    else if(s.equals("removeAd")||s.equals("removeCus"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/removeAorC.fxml"))));
	    else if(s.equals("addToCart"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/addToCart.fxml"))));
	    else if(s.equals("LogoutHbox")||s.equals("back")||s.equals("create")||s.equals("change"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/login.fxml"))));
	    else if(s.equals("credit"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/CreditCard.fxml"))));
	    else if(s.equals("loginB"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/Home.fxml"))));
	    else if(s.equals("fpass"))
	    	stage.setScene(new Scene(FXMLLoader.load(StageFactory.class.getResource("../Views/Cpass.fxml"))));
	    stage.getIcons().add(new Image(StageFactory.class.getResourceAsStream("../Resources/logo.png")));
	    MyDecoration.Fade(stage);
	    MyDecoration.makeDraggable(stage);
	    return stage;
	}
}
