package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Model.DB;
import sample.Model.FXMLFactory;
import sample.Model.MyDecoration;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
        if(DB.setupCon()==1) {
        	primaryStage.setScene(new Scene((Parent) FXMLFactory.get("login")));
        	primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("Resources/logo.png")));
            MyDecoration.Fade(primaryStage);
    	    MyDecoration.makeDraggable(primaryStage);
            primaryStage.show();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}