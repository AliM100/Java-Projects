package sample.Model;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MyDecoration {
    private static double x,y;
    public static void makeDraggable(Stage stage)
    {	
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene=stage.getScene();
        //drag it here
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }
    public static void Fade(Stage stage)
    {	
    	FadeTransition ft = new FadeTransition(Duration.millis(600), stage.getScene().getRoot());
    	ft.setFromValue(0.0);
    	ft.setToValue(1.0);
    	ft.play();
    }
    public static void FadeS(AnchorPane stage)
    {	
    	FadeTransition ft = new FadeTransition(Duration.millis(600), stage);
    	ft.setFromValue(0.0);
    	ft.setToValue(1.0);
    	ft.play();
    }
}